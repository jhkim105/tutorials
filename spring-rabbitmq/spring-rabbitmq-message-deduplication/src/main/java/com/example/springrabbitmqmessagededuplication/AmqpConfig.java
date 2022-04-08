package com.example.springrabbitmqmessagededuplication;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  public static final String QUEUE_DD = "deduplicate";

  @Bean
  Queue deduplicateQueue() {
    return QueueBuilder
        .durable(QUEUE_DD)
        .autoDelete()
        .withArgument("x-message-deduplication", true)
        .build();
  }
}
