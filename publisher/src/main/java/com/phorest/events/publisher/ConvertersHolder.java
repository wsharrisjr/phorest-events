package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ConvertersHolder {

    @Autowired(required = false)
    private List<EventSourceToEventConverter<? extends EventSource>> converters = Collections.emptyList();

    private Map<Class<?>, EventSourceToEventConverter<EventSource>> convertersMapping;

    @PostConstruct
    public void init() {
        convertersMapping = converters.stream()
                .collect(Collectors.toMap(EventSourceToEventConverter::getSupportedType, converter -> (EventSourceToEventConverter<EventSource>) converter));
    }

    public EventSourceToEventConverter<EventSource> getConverter(Class<?> eventSourceType) {
        return convertersMapping.get(eventSourceType);
    }
}

