package com.phorest.events.configuration;

import com.phorest.events.configuration.properties.RabbitProperties;
import com.phorest.events.configuration.properties.Ssl;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.RabbitConnectionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("amqp")
@Configuration
@Conditional(RabbitConnectionConfigurationCondition.class)
public class RabbitConnectionConfiguration {

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(RabbitProperties config) throws Exception {
        RabbitConnectionFactoryBean factory = new RabbitConnectionFactoryBean();
        factory.setUsername(config.getUsername());
        factory.setPassword(config.getPassword());
        factory.setVirtualHost(config.getVirtualHost());
        factory.setConnectionTimeout(config.getConnectionTimeoutInMillis());
        factory.setRequestedHeartbeat(config.getRequestedHeartbeat());
        configureSslIfNeeded(config, factory);
        factory.afterPropertiesSet();
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(factory.getObject());
        connectionFactory.setAddresses(config.getAddresses());
        return connectionFactory;
    }

    private void configureSslIfNeeded(RabbitProperties config, RabbitConnectionFactoryBean factory) {
        Ssl ssl = config.getSsl();
        factory.setUseSSL(ssl.isEnabled());
        factory.setKeyStore(ssl.getKeyStore());
        factory.setKeyStorePassphrase(ssl.getKeyStorePassword());
        factory.setTrustStore(ssl.getTrustStore());
        factory.setTrustStorePassphrase(ssl.getTrustStorePassword());
    }

}
