package com.phorest.events.publisher;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAspectJAutoProxy
@EnableAsync
@Configuration
public class PublisherConfiguration {
}
