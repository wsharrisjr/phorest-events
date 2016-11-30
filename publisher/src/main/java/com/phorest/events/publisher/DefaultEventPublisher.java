package com.phorest.events.publisher;

import com.codahale.metrics.annotation.Timed;
import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;
import com.phorest.events.publisher.headers.Header;
import com.phorest.events.publisher.headers.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Profile("amqp")
@Component
public class DefaultEventPublisher implements EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventPublisher.class);

    @Autowired
    private RabbitOperations rabbitOperations;

    @Autowired
    private ConvertersHolder convertersHolder;

    @Override
    @Timed(name = "convert-and-publish-event-default")
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource, Header... headers) {
        logger.info("About to publish event to exchange: {}, with routingKeyTemplate: {}", exchange, routingKeyTemplate);
        String routingKey = RoutingKeyResolver.resolve(routingKeyTemplate, eventSource);
        publishEvent(exchange, routingKey, convertToEvent(eventSource), headers);
        logger.info("Published event to exchange: {}, with routingKey: {}", exchange, routingKey);
    }

    @Override
    @Timed(name = "publish-event-default")
    public <T extends EventData> void publishEvent(String exchange, String routingKey, Event<T> event, Header... headers) {
        logger.info("About to publish event to exchange: {}, with routingKey: {}", exchange, routingKey);
        rabbitOperations.convertAndSend(exchange, routingKey, event, getMessagePostProcessor(exchange, routingKey, headers));
        logger.info("Published event to exchange: {}, with routingKey: {}", exchange, routingKey);
    }

    private Event<EventData> convertToEvent(EventSource eventSource) {
        EventSourceToEventConverter<EventSource> eventSourceToEventConverter = convertersHolder.getConverter(eventSource.getClass());
        Assert.notNull(eventSourceToEventConverter);
        return eventSourceToEventConverter.convert(eventSource);
    }

    private MessagePostProcessor getMessagePostProcessor(String exchange, String routingKey, Header[] headers) {
        return message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            addHeaders(headers, messageProperties);
            addDefaultHeaders(exchange, routingKey, messageProperties);
            return message;
        };
    }

    private void addHeaders(Header[] headers, MessageProperties messageProperties) {
        for (Header header : headers) {
            messageProperties.setHeader(header.getKey(), header.getValue());
        }
    }

    private void addDefaultHeaders(String exchange, String routingKey, MessageProperties messageProperties) {
        messageProperties.setHeader(Headers.ORIGINAL_EXCHANGE, exchange);
        messageProperties.setHeader(Headers.ORIGINAL_ROUTING_KEY, routingKey);
    }
}
