package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@Slf4j
public class InboundConfig {

  @Bean
  public Queue testQueue() {
    return new Queue("foo");
  }

  @Bean
  public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
    return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, "foo"))
        .handle(this, "handle")
        .get();
  }

  public void handle(String message) {
    log.debug(message);
  }

}
