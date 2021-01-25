package com.example.demo;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  public static final String QUEUE_MESSAGES = "messages-queue";
  public static final String EXCHANGE_MESSAGES = "messages-exchange";
  public static final String QUEUE_MESSAGES_DLQ = QUEUE_MESSAGES +".dlq";
  public static final String EXCHANGE_MESSAGES_DLX = QUEUE_MESSAGES + ".dlx";
  public static final String HEADER_X_RETRIES_COUNT = "x-retries-count";

  @Bean
  FanoutExchange deadLetterExchange() {
    return new FanoutExchange(EXCHANGE_MESSAGES_DLX);
  }

  @Bean
  Queue deadLetterQueue() {
    return QueueBuilder.durable(QUEUE_MESSAGES_DLQ).build();
  }

  @Bean
  Binding dealLetterBinding() {
    return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
  }

  @Bean
  Queue messagesQueue() {
    return QueueBuilder.durable(QUEUE_MESSAGES)
        .withArgument("x-dead-letter-exchange", EXCHANGE_MESSAGES_DLX)
        .build();
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
