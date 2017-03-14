package com.phorest.events.configuration.retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RetryDelayCalculator {

    private int minDelay;
    private int maxDelay;
    private int backOffBase;

    @Autowired
    public RetryDelayCalculator(@Value("${phorest.events.configuration.rabbit.retry.minDelay:60}") int minDelay,
                                @Value("${phorest.events.configuration.rabbit.retry.maxDelay:3600}") int maxDelay,
                                @Value("${phorest.events.configuration.rabbit.retry.backoffBase:3}") int backOffBase) {
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.backOffBase = backOffBase;
    }

    public long getDelayValue(int redeliveryCount) {
        return TimeUnit.SECONDS.toMillis(getDelayInSeconds(redeliveryCount));
    }

    private long getDelayInSeconds(int redeliveryCount) {
        long exponentialDelay = getExponentialDelay(redeliveryCount);
        long exponentialOrMinDelay = Math.max(minDelay, exponentialDelay);
        return Math.min(exponentialOrMinDelay, maxDelay);
    }

    private long getExponentialDelay(int redeliveryCount) {
        return (long) Math.pow(backOffBase, redeliveryCount);
    }
}
