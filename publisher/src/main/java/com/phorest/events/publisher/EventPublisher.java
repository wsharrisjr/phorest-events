package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;

public interface EventPublisher {

    void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource);
}
