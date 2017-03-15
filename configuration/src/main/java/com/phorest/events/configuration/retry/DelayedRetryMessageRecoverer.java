package com.phorest.events.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;

import java.util.Map;


public class DelayedRetryMessageRecoverer implements MessageRecoverer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String X_RETRY_COUNT = "x-retry-count";

    private AmqpTemplate amqpTemplate;

    private RetryDelayCalculator retryDelayCalculator;

    private int maxRetryCount = 5;


    public DelayedRetryMessageRecoverer(AmqpTemplate amqpTemplate, RetryDelayCalculator retryDelayCalculator, int maxRetryCount) {
        this.amqpTemplate = amqpTemplate;
        this.retryDelayCalculator = retryDelayCalculator;
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public void recover(Message message, Throwable cause) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String originalRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        int retryCount = (int) headers.getOrDefault(X_RETRY_COUNT, 0);

        if (retryCount < maxRetryCount) {
            log.warn("Retrying to deliver for routing key {}. Attempts: {}, reason: {}", originalRoutingKey, retryCount, cause.getMessage());
            headers.put(X_RETRY_COUNT, ++retryCount);
            message.getMessageProperties().setDelay((int) retryDelayCalculator.getDelayValue(retryCount));
            amqpTemplate.send(message.getMessageProperties().getReceivedExchange(), originalRoutingKey, message);
        } else {
            log.error("Message failed to deliver for routing key {} after {} attempts", originalRoutingKey, retryCount);
            log.error("Messaged failure reason: {}", cause.getMessage(), cause);
        }
    }
}
