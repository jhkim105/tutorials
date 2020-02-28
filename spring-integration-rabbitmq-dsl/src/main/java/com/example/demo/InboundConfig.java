package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@Slf4j
public class InboundConfig {

  @Bean // return the upper cased payload
  public IntegrationFlow amqpInboundGateway(ConnectionFactory connectionFactory) {
    return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, "foo"))
        .transform(String.class, String::toUpperCase)
        .handle(this, "handle")
        .get();
  }


  public String handleAndReply(String message) {
    log.debug("message:{}", message);
    return String.format("reply to %s", message);
  }

  public void handle(String message) {
    log.debug("message:{}", message);
  }
}
