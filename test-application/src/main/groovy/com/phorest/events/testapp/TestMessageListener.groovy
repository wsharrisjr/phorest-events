package com.phorest.events.testapp

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Argument
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_DLQ_ROUTING_KEY
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_ERROR_EXCHANGE
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_EXCHANGE
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_QUEUE
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_ROUTING_KEY

@RestController
@Slf4j
class TestMessageListener {

    Closure<TestMessage> messageAction = { TestMessage message ->
        log.info("Do nothing action")
        message
    }

    @RabbitListener(bindings = @QueueBinding(
        value = @Queue(value = TEST_QUEUE, durable = "true", arguments = [
            @Argument(name = "x-dead-letter-exchange", value = TEST_ERROR_EXCHANGE),
            @Argument(name = "x-dead-letter-routing-key", value = TEST_DLQ_ROUTING_KEY)
        ]),
        exchange = @Exchange(value = TEST_EXCHANGE, type = ExchangeTypes.TOPIC, delayed = "true", durable = "true"),
        key = TEST_ROUTING_KEY)
    )
    @Transactional
    void onEvent(@Valid TestMessage message) {
        log.info("event received on test queue. Message: {}", message)
        messageAction(message)
    }

}
