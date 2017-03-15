package com.phorest.events.configuration;

import com.phorest.events.configuration.retry.DelayedRetryMessageRecoverer;
import com.phorest.events.configuration.retry.RetryDelayCalculator;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.transaction.PlatformTransactionManager;


@Profile("amqp")
@Configuration
@EnableRabbit
public class RabbitListenerConfiguration {

    @Autowired
    private RetryDelayCalculator retryDelayCalculator;

    @Autowired(required = false)
    private PlatformTransactionManager transactionManager;

    @Value("${phorest.events.configuration.rabbit.retry.maxRetryAttempts:10}")
    private int maxRetryAttempts;

    @Value("${phorest.events.configuration.rabbit.maxConcurrentConsumers:30}")
    private int maxConcurrentConsumers;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, RetryOperationsInterceptor retryInterceptor, Jackson2JsonMessageConverter jsonConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setAdviceChain(retryInterceptor);
        if (transactionManager != null) {
            factory.setTransactionManager(transactionManager);
        }
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    RetryOperationsInterceptor retryInterceptor(RabbitTemplate rabbitTemplate) {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(1)
                .recoverer(new DelayedRetryMessageRecoverer(rabbitTemplate, retryDelayCalculator, maxRetryAttempts))
                .build();
    }

}
