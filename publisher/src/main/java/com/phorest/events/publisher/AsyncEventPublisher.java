package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Profile("amqp")
@Component
public class AsyncEventPublisher implements EventPublisher {

    @Autowired
    private EventPublisher defaultEventPublisher;

    @Override
    @Async("eventPublisherExecutor")
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource) {
        defaultEventPublisher.publishEvent(exchange, routingKeyTemplate, eventSource);
    }
}
