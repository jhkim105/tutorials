package com.example.demo;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AmqpConfig {

  static final String QUEUE_A = "queue-a";



  @Bean
  Queue queue() {
    return new Queue(QUEUE_A, false);
  }

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(QUEUE_A);
    container.setMessageListener(new CustomBatchMessageListener());
    container.setBatchSize(10);
    container.setConsumerBatchEnabled(true);
//    container.setDeBatchingEnabled(true);
    return container;
  }

}
