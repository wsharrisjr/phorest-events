package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ConvertersHolder {

    @Autowired
    private List<EventSourceToEventConverter<? extends EventSource>> converters;

    private Map<Class<?>, EventSourceToEventConverter<EventSource>> convertersMapping;

    @PostConstruct
    public void init() {
        Assert.notEmpty(converters, "At least one bean of type 'TypeToEventConverter<? extends EventSource>' has to be defined");
        convertersMapping = converters.stream()
            .collect(Collectors.toMap(EventSourceToEventConverter::getSupportedType, converter -> (EventSourceToEventConverter<EventSource>) converter));
    }

    public EventSourceToEventConverter<EventSource> getConverter(Class<?> eventSourceType) {
        return convertersMapping.get(eventSourceType);
    }
}

