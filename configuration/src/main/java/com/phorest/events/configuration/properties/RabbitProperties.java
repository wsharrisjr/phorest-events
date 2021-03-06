package com.phorest.events.configuration.properties;

import com.phorest.events.configuration.RabbitConnectionConfigurationCondition;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("amqp")
@Conditional(RabbitConnectionConfigurationCondition.class)
@Component
@Data
public class RabbitProperties {

    @Value("${phorest.events.configuration.rabbit.username}")
    private String username;

    @Value("${phorest.events.configuration.rabbit.password}")
    private String password;

    @Value("${phorest.events.configuration.rabbit.virtualHost}")
    private String virtualHost;

    @Value("${phorest.events.configuration.rabbit.addresses}")
    private String addresses;

    @Value("${phorest.events.configuration.rabbit.connectionTimeoutInMillis:10000}")
    private int connectionTimeoutInMillis;

    @Value("${phorest.events.configuration.rabbit.requestedHeartbeatInSeconds:60}")
    private int requestedHeartbeat;

    @Value("${phorest.events.configuration.rabbit.autoRecoveryEnabled:true}")
    private boolean autoRecoveryEnabled;

    @Autowired
    private Ssl ssl;
}
