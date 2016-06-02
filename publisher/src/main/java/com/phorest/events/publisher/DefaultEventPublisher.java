package com.phorest.events.publisher;

import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
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
    @Async
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource) {
        logger.info("About to publish event to exchange: {}, with routingKeyTemplate: {}", exchange, routingKeyTemplate);
        String routingKey = RoutingKeyResolver.resolve(routingKeyTemplate, eventSource);
        rabbitOperations.convertAndSend(exchange, routingKey, convertToEvent(eventSource));
        logger.info("Published event to exchange: {}, with routingKey: {}", exchange, routingKey);
    }

    private Event<EventData> convertToEvent(EventSource eventSource) {
        EventSourceToEventConverter<EventSource> eventSourceToEventConverter = convertersHolder.getConverter(eventSource.getClass());
        Assert.notNull(eventSourceToEventConverter);
        return eventSourceToEventConverter.convert(eventSource);
    }
}
