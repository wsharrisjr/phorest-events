package com.phorest.events.configuration.properties;

import com.phorest.events.configuration.RabbitConnectionConfigurationCondition;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Conditional(RabbitConnectionConfigurationCondition.class)
@Component
@Data
public class Ssl {

    @Value("${phorest.events.configuration.rabbit.ssl.enabled:false}")
    private boolean enabled;

    @Value("${phorest.events.configuration.rabbit.ssl.keyStore:#{null}}")
    private String keyStore;

    @Value("${phorest.events.configuration.rabbit.ssl.keyStorePassword:#{null}}")
    private String keyStorePassword;

    @Value("${phorest.events.configuration.rabbit.ssl.trustStore:#{null}}")
    private String trustStore;

    @Value("${phorest.events.configuration.rabbit.ssl.trustStorePassword:#{null}}")
    private String trustStorePassword;
}