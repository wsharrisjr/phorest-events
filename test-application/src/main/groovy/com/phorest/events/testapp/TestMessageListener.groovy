package com.phorest.events.testapp

import groovy.util.logging.Slf4j
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class TestMessageListener {

    private Map<String, Integer> messegesCount = [:]

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "test-queue", durable = "true"),
            exchange = @Exchange(value = "test", type = ExchangeTypes.TOPIC, delayed = "true", durable = "true"),
            key = "test")
    )
    void onEvent(String message) {
        log.info("event received on test queue. Message: {}", message)
        messegesCount[message] = messegesCount[message] ? messegesCount[message] + 1 : 1

        if ('fail' == message) {
            throw new RuntimeException('test error')
        }
    }

    @RequestMapping("/messagesCount")
    Map<String, Integer> getMessagesCount() {
        messegesCount
    }

    @RequestMapping(value = "/messagesCount", method = RequestMethod.DELETE)
    void resetCount() {
        messegesCount.clear()
    }

    @RequestMapping('health')
    String health() {
        'OK'
    }
}
