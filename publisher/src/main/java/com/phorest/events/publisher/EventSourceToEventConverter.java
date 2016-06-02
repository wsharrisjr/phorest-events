package com.phorest.events.publisher;

import com.phorest.events.model.Event;
import com.phorest.events.model.EventData;
import com.phorest.events.model.EventSource;

public interface EventSourceToEventConverter<S extends EventSource> {

    Event<EventData> convert(S source);

    Class<?> getSupportedType();
}
