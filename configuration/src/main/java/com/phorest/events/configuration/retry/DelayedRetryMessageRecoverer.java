package com.phorest.events.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
    private RejectAndDontRequeueRecoverer rejectAndDontRequeueRecoverer;

    @Autowired(required = false)
    private RetryDecider retryDecider;

    @Value("${phorest.events.configuration.rabbit.retry.maxRetryAttempts:10}")
    private int maxRetryAttempts;

    @PostConstruct
    public void init() {
        if (retryDecider == null) {
            retryDecider = new RetryDecider();
        }
    }

    @Override
    public void recover(Message message, Throwable cause) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        String originalRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
        int retryCount = (int) headers.getOrDefault(X_RETRY_COUNT, 0);

        if (shouldRetry(retryCount, cause)) {
            log.warn("Retrying to deliver for routing key {}. Attempts: {}, reason: {}", originalRoutingKey, retryCount, cause.getMessage());
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

    private void rejectMessage(Message message, Throwable cause) {
        rejectAndDontRequeueRecoverer.recover(message, cause);
    }

}
