package com.phorest.events.publisher;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Profile("amqp")
@EnableAsync
@Configuration
public class PublisherConfiguration {

    @Value("${phorest.events.publisher.executor.corePoolSize:2}")
    private int corePoolSize;

    @Value("${phorest.events.publisher.executor.maxPoolSize:5}")
    private int maxPoolSize;

    @Value("${phorest.events.publisher.executor.queueCapacity:50}")
    private int queueCapacity;

    @Bean
    public Executor eventPublisherExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("EventPublisherExecutor-");
        executor.initialize();
        return executor;
    }
}
