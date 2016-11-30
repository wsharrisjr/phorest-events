package com.phorest.events.publisher;

import com.codahale.metrics.annotation.Timed;
import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;
import com.phorest.events.publisher.headers.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!amqp")
@Component
public class StubEventPublisher implements EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(StubEventPublisher.class);

    @Override
    @Timed(name = "publish-event-stub")
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource, Header... headers) {
        if (logger.isDebugEnabled()) {
            logger.debug("Publish event called in stub with exchange: {}, routingKeyTemplate: {}, routingKeyArgs: {}, eventSource type: {}, headers: {}", exchange, routingKeyTemplate,
                    eventSource.getRoutingKeyArgs(), eventSource.getClass(), headers);
        }
    }

    @Override
    public <T extends EventData> void publishEvent(String exchange, String routingKey, Event<T> event, Header... headers) {
        if (logger.isDebugEnabled()) {
            logger.debug("Publish event called in stub with exchange: {}, routingKey: {}, routingKeyArgs: {}, event: {}, headers: {}", exchange, routingKey, event, headers);
        }
    }
}
