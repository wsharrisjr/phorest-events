package com.phorest.events.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Profile("amqp")
@Component
public class DelayedRetryMessageRecoverer implements MessageRecoverer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String X_RETRY_COUNT = "x-retry-count";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RetryDelayCalculator retryDelayCalculator;

    @Autowired
    private List<Queue> queues;

    @Autowired(required = false)
    private DeadLetterPublishingMessageRecoverer deadLetterPublishingMessageRecoverer;

    @Autowired(required = false)
    private RetryDecider retryDecider;

    @Value("${phorest.events.configuration.rabbit.retry.maxRetryAttempts:10}")
    private int maxRetryAttempts;


    @PostConstruct
    public void init() {
        if (retryDecider == null) {
            retryDecider = new RetryDecider();
        }

        if (deadLetterPublishingMessageRecoverer == null) {
            deadLetterPublishingMessageRecoverer = new DeadLetterPublishingMessageRecoverer(rabbitTemplate, queues);
        }
    }

    @Override
    public void recover(Message message, Throwable cause) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String originalRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        int retryCount = (int) headers.getOrDefault(X_RETRY_COUNT, 0);

        if (shouldRetry(retryCount, cause)) {
            log.warn("Retrying to deliver for routing key {}. Attempts: {}, reason: {}", originalRoutingKey, retryCount, getCauseMessage(cause));
            headers.put(X_RETRY_COUNT, ++retryCount);
            message.getMessageProperties().setDelay((int) retryDelayCalculator.getDelayValue(retryCount));
            rabbitTemplate.send(message.getMessageProperties().getReceivedExchange(), originalRoutingKey, message);
        } else {
            log.error("Message failed to deliver for routing key {} after {} attempts for reason: ", originalRoutingKey, retryCount, cause);
            rejectMessage(message, cause);
        }
    }

    private boolean shouldRetry(int retryCount, Throwable cause) {
        return isMaxRetryCountNotReached(retryCount) && retryDecider.isRetryableException(cause);
    }

    private boolean isMaxRetryCountNotReached(int retryCount) {
        return retryCount < maxRetryAttempts;
    }

    private String getCauseMessage(Throwable cause) {
        return cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage();
    }

    private void rejectMessage(Message message, Throwable cause) {
        deadLetterPublishingMessageRecoverer.recover(message, cause);
    }

}
