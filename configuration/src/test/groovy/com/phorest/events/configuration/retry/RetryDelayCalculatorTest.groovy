package com.phorest.events.configuration.retry

import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.TimeUnit

class RetryDelayCalculatorTest extends Specification {

    @Unroll
    def "should calculate retry delay"() {
        given:
        int minDelay = 60
        int maxDelay = 3600
        int backOffBase = 3
        RetryDelayCalculator calculator = new RetryDelayCalculator(minDelay, maxDelay, backOffBase)

        when:
        long delayInMillis = calculator.getDelayValue(redeliveryCount)

        then:
        delayInMillis == TimeUnit.SECONDS.toMillis(expectedDelay)

        where:
        redeliveryCount | expectedDelay
        1               | 60L
        2               | 60L
        3               | 60L
        4               | 81L
        5               | 243L
        6               | 729L
        7               | 2_187L
        8               | 3_600L
        9               | 3_600L
        10              | 3_600L
    }
}
