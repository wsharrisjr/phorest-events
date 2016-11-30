package com.phorest.events.publisher;

import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;
import com.phorest.events.publisher.headers.Header;
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
    public void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource, Header... headers) {
        defaultEventPublisher.publishEvent(exchange, routingKeyTemplate, eventSource, headers);
    }

    @Override
    @Async("eventPublisherExecutor")
    public <T extends EventData> void publishEvent(String exchange, String routingKey, Event<T> event, Header... headers) {
        defaultEventPublisher.publishEvent(exchange, routingKey, event, headers);
    }
}
