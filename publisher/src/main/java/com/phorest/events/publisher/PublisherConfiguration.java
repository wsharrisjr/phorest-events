package com.phorest.events.publisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

@Profile("amqp")
@EnableAsync
@Configuration
public class PublisherConfiguration {
}
