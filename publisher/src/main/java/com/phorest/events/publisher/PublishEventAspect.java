package com.phorest.events.publisher;

import com.phorest.events.model.EventSource;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PublishEventAspect {

    @Autowired
    private EventPublisher eventPublisher;

    @AfterReturning(pointcut = "@annotation(publishEvent)", returning = "eventSource")
    public void publishEvent(PublishEvent publishEvent, EventSource eventSource) {
        eventPublisher.publishEvent(publishEvent.exchange(), publishEvent.routingKey(), eventSource);
    }
}
