package com.phorest.events.publisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@Profile("amqp")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableAsync
@Configuration
public class PublisherConfiguration {
}
