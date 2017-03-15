package com.phorest.events.configuration.retry

import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageProperties
import spock.lang.Specification

import static com.phorest.events.configuration.retry.DelayedRetryMessageRecoverer.X_RETRY_COUNT


class DelayedRetryMessageRecovererTest extends Specification {

    AmqpTemplate amqpTemplate = Mock(AmqpTemplate)
    DelayedRetryMessageRecoverer recoverer = new DelayedRetryMessageRecoverer(amqpTemplate, new RetryDelayCalculator(1, 1, 3), 2)

    def 'should add count header for failed message and resend'() {
        given:
        MessageProperties properties = new MessageProperties();
        properties.receivedRoutingKey = 'key'
        properties.receivedExchange = 'exchange'
        Message message = new Message('asdf'.bytes, properties)

        when:
        recoverer.recover(message, new Throwable("fail"))

        then:
        1 * amqpTemplate.send(*_) >> { args ->
            assert args[0] == properties.receivedExchange
            assert args[1] == properties.receivedRoutingKey
            assert (args[2] as Message).body == message.body
            assert (args[2] as Message).messageProperties.getHeaders().get(X_RETRY_COUNT) == 1
            assert (args[2] as Message).messageProperties.getDelay() == 1000
        }
    }

    def 'should stop when reached max count'() {
        given:
        MessageProperties properties = new MessageProperties();
        properties.receivedRoutingKey = 'key'
        properties.receivedExchange = 'exchange'
        properties.getHeaders().put(X_RETRY_COUNT, 5)
        Message message = new Message('asdf'.bytes, properties)

        when:
        recoverer.recover(message, new Throwable("fail"))

        then:
        0 * amqpTemplate.send(*_)
    }
}
