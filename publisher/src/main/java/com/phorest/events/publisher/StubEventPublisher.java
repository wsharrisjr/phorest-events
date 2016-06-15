package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!amqp")
@Component
public class StubEventPublisher implements EventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(StubEventPublisher.class);

    @Override
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource) {
        if (logger.isDebugEnabled()) {
            logger.debug("Publish event called in stub with exchange: {}, routingKeyTemplate: {}, routingKeyArgs: {}, eventSource type: {}", exchange, routingKeyTemplate, eventSource.getRoutingKeyArgs(), eventSource.getClass());
        }
    }
}
