package com.phorest.events.publisher;

import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;
import com.phorest.events.publisher.headers.Header;

public interface EventPublisher {

    void publishEvent(String exchange, String routingKeyTemplate, EventSource eventSource, Header... headers);

    <T extends EventData> void publishEvent(String exchange, String routingKey, Event<T> event, Header... headers);
}
