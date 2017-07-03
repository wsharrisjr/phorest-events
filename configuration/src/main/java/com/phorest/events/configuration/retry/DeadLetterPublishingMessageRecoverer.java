package com.phorest.events.configuration.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.*;

public class DeadLetterPublishingMessageRecoverer implements MessageRecoverer {

    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterPublishingMessageRecoverer.class);

    private RabbitTemplate rabbitTemplate;
    private Map<String, Queue> queuesByName;

    public DeadLetterPublishingMessageRecoverer(RabbitTemplate rabbitTemplate, List<Queue> queues) {
        this.rabbitTemplate = rabbitTemplate;
        this.queuesByName = queues.stream()
                .collect(Collectors.toMap(Queue::getName, Function.identity()));
    }

    @Override
    public void recover(Message message, Throwable cause) {
        String consumerQueueName = message.getMessageProperties().getConsumerQueue();
        Queue consumerQueue = queuesByName.get(consumerQueueName);
        Map<String, Object> queueArguments = consumerQueue.getArguments();
        Object deadLetterExchange = queueArguments.get(X_DEAD_LETTER_EXCHANGE);
        Object deadLetterRoutingKey = queueArguments.get(X_DEAD_LETTER_ROUTING_KEY);

        if (deadLetterExchange == null || deadLetterRoutingKey == null) {
            logger.info("No dead letter exchange/routing key defined for queue: {}. Dropping message: {}", consumerQueueName, message.getMessageProperties().getMessageId());
            return;
        }
        appendErrorHeaders(message, cause);
        rabbitTemplate.send(deadLetterExchange.toString(), deadLetterRoutingKey.toString(), message);
        logger.warn("Republishing failed message to exchange: {} with routing key: {}", deadLetterExchange, deadLetterRoutingKey);

    }

    private void appendErrorHeaders(Message message, Throwable cause) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        headers.put(X_EXCEPTION_STACKTRACE, getStackTraceAsString(cause));
        headers.put(X_EXCEPTION_MESSAGE, cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
        headers.put(X_ORIGINAL_EXCHANGE, message.getMessageProperties().getReceivedExchange());
        headers.put(X_ORIGINAL_ROUTING_KEY, message.getMessageProperties().getReceivedRoutingKey());
    }

    protected void appendAdditionalErroHeaders(Message message, Throwable cause) {
        //subclasses can override this method
    }

    private String getStackTraceAsString(Throwable cause) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        cause.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
}
