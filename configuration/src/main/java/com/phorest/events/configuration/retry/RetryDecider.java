package com.phorest.events.configuration.retry;

import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RetryDecider {

    private final List<Class<?>> nonRetryableExceptions = new ArrayList<>();

    {
        nonRetryableExceptions.add(MethodArgumentNotValidException.class);
    }

    public RetryDecider(Class<?>... nonRetryableExceptions) {
        this.nonRetryableExceptions.addAll(Arrays.asList(nonRetryableExceptions));
    }

    public boolean isRetryableException(Throwable ex) {
        Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
        return nonRetryableExceptions.stream()
                .noneMatch(aClass -> aClass.isInstance(cause));
    }
}
