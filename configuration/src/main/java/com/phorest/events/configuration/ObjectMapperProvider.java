package com.phorest.events.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("amqp")
public class ObjectMapperProvider {

    @Autowired(required = false)
    @Qualifier("rabbitObjectMapper")
    private ObjectMapper rabbitObjectMapper;

    @Autowired
    private ObjectMapper objectMapper;


    public ObjectMapper getObjectMapper() {
        return rabbitObjectMapper != null ? rabbitObjectMapper : objectMapper;
    }
}
