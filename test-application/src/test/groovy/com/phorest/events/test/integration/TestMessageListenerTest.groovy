package com.phorest.events.test.integration

import com.phorest.events.configuration.retry.DelayedRetryMessageRecoverer
import com.phorest.events.testapp.Application
import com.phorest.events.testapp.NonRetryableException
import com.phorest.events.testapp.TestMessage
import com.phorest.events.testapp.TestMessageListener
import org.awaitility.Awaitility
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

import java.util.concurrent.TimeUnit

import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_DLQ
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_EXCHANGE
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_QUEUE
import static com.phorest.events.testapp.TestRabbitConfiguration.TEST_ROUTING_KEY

@ContextConfiguration(classes = Application)
@SpringBootTest
@ActiveProfiles("amqp")
@TestPropertySource(properties = [
    "phorest.events.configuration.rabbit.retry.maxRetryAttempts=3",
    "phorest.events.configuration.rabbit.retry.minDelay=0",
    "phorest.events.configuration.rabbit.retry.maxDelay=0",
    "phorest.events.configuration.rabbit.retry.backoffBase=0",
])
class TestMessageListenerTest extends Specification {

    @Autowired
    TestMessageListener listener

    @Autowired
    RabbitTemplate rabbitTemplate

    @Autowired
    AmqpAdmin amqpAdmin

    void setup() {
        amqpAdmin.purgeQueue(TEST_QUEUE, false)
        amqpAdmin.purgeQueue(TEST_DLQ, false)
    }

    def 'it should receive valid message'() {
        given:
        TestMessage message = new TestMessage('some text')
        List<TestMessage> result = []
        listener.messageAction = { TestMessage m ->
            result << m
            m
        }

        when:
        rabbitTemplate.convertAndSend(TEST_EXCHANGE, TEST_ROUTING_KEY, message)

        then:
        Awaitility.await().atMost(10, TimeUnit.SECONDS).until {
            result.size() > 0
        }
        result.size() == 1
        result[0] == message
    }

    def 'it should send invalid message immediately to error queue'() {
        TestMessage message = new TestMessage()

        when:
        rabbitTemplate.convertAndSend(TEST_EXCHANGE, TEST_ROUTING_KEY, message)

        then:
        Message deadLetteredMessage = null
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until {
            deadLetteredMessage = rabbitTemplate.receive(TEST_DLQ, 1000)
            deadLetteredMessage != null
        }

        def headers = deadLetteredMessage.getMessageProperties().getHeaders()
        !headers[DelayedRetryMessageRecoverer.X_RETRY_COUNT]
    }

    def 'it should retry processing message when retryable exception occurs and then send to error queue once retries are exhausted'() {
        given:
        TestMessage message = new TestMessage('some message')
        listener.messageAction = { TestMessage m ->
            throw new IllegalArgumentException("Invalid message text: ${m.text}")
        } as Closure<TestMessage>

        when:
        rabbitTemplate.convertAndSend(TEST_EXCHANGE, TEST_ROUTING_KEY, message)

        then:
        Message deadLetteredMessage = null
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until {
            deadLetteredMessage = rabbitTemplate.receive(TEST_DLQ, 1000)
            deadLetteredMessage != null
        }

        def headers = deadLetteredMessage.getMessageProperties().getHeaders()
        headers[DelayedRetryMessageRecoverer.X_RETRY_COUNT] == 3
    }

    def 'it should send message immediately to error queue when non retryable exception occurs'() {
        TestMessage message = new TestMessage('some message')
        listener.messageAction = { TestMessage m ->
            throw new NonRetryableException('Non retryable error')
        } as Closure<TestMessage>

        when:
        rabbitTemplate.convertAndSend(TEST_EXCHANGE, TEST_ROUTING_KEY, message)

        then:
        Message deadLetteredMessage = null
        Awaitility.await().atMost(5, TimeUnit.SECONDS).until {
            deadLetteredMessage = rabbitTemplate.receive(TEST_DLQ, 1000)
            deadLetteredMessage != null
        }

        def headers = deadLetteredMessage.getMessageProperties().getHeaders()
        !headers[DelayedRetryMessageRecoverer.X_RETRY_COUNT]
    }
}
