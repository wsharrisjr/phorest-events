package com.phorest.events.testapp

import com.phorest.events.configuration.retry.RetryDecider
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class TestRabbitConfiguration {

    static final Long TWO_DAYS_IN_MILLIS = 172_800_000L

    public static final String TEST_QUEUE = 'test-queue'
    public static final String TEST_EXCHANGE = 'test'
    public static final String TEST_ROUTING_KEY = 'test'
    public static final String TEST_DLQ = 'test-dlq'
    public static final String TEST_DLQ_ROUTING_KEY = 'test.error'
    public static final String TEST_ERROR_EXCHANGE = 'test-errors'

    @Bean
    Binding testDlqBinding() {
        BindingBuilder.bind(testDlq()).to(errorsExchange()).with(TEST_DLQ_ROUTING_KEY)
    }

    @Bean
    DirectExchange errorsExchange(){
        new DirectExchange(TEST_ERROR_EXCHANGE, true, false)
    }

    @Bean
    Queue testDlq() {
        new Queue(TEST_DLQ, true, false, false, ["x-message-ttl": TWO_DAYS_IN_MILLIS])
    }

    @Bean
    RetryDecider retryDecider() {
        new RetryDecider(NonRetryableException)
    }

    @Bean
    PlatformTransactionManager platformTransactionManager(ConnectionFactory connectionFactory) {
        new RabbitTransactionManager(connectionFactory)
    }
}
