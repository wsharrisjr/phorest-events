package com.phorest.events.configuration;

import com.phorest.events.configuration.retry.DelayedRetryMessageRecoverer;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean;


@Profile("amqp")
@Configuration
@EnableRabbit
public class RabbitListenerConfiguration implements RabbitListenerConfigurer {

    @Value("${phorest.events.configuration.rabbit.maxConcurrentConsumers:30}")
    private int maxConcurrentConsumers;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(validatingHandlerMethodFactory());
    }

    @Bean
    DefaultMessageHandlerMethodFactory validatingHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setValidator(amqpValidator());
        return factory;
    }

    @Bean
    Validator amqpValidator() {
        return new OptionalValidatorFactoryBean();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, RetryOperationsInterceptor retryInterceptor, Jackson2JsonMessageConverter jsonConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        factory.setAdviceChain(retryInterceptor);
        factory.setDefaultRequeueRejected(false);
        factory.setMessageConverter(jsonConverter);
        return factory;
    }

    @Bean
    RetryOperationsInterceptor retryInterceptor(DelayedRetryMessageRecoverer delayedRetryMessageRecoverer) {
        return RetryInterceptorBuilder.stateless()
                .maxAttempts(1)
                .recoverer(delayedRetryMessageRecoverer)
                .build();
    }

}
