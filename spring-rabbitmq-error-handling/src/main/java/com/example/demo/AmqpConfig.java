package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  public static final String QUEUE_MESSAGES = "messages-queue";
  public static final String EXCHANGE_MESSAGES = "messages-exchange";

  @Bean
  Queue messagesQueue() {
    return QueueBuilder.durable(QUEUE_MESSAGES).build();
  }

  @Bean
  DirectExchange messageExchange() {
    return new DirectExchange(EXCHANGE_MESSAGES);
  }

  @Bean
  Binding bindingMessages() {
    return BindingBuilder.bind(messagesQueue()).to(messageExchange()).with(QUEUE_MESSAGES);
  }

}
