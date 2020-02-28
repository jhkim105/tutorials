package com.example.demo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.amqp.outbound.AmqpOutboundEndpoint;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OutboundConfig {

  @Bean
  public IntegrationFlow amqpOutboundFlow(AmqpTemplate amqpTemplate) {
    return IntegrationFlows.from(amqpOutboundChannel())
        .handle(Amqp.outboundAdapter(amqpTemplate)
            .routingKey("foo")) // default exchange - route to queue 'foo'
        .get();
  }

  @Bean
  public MessageChannel amqpOutboundChannel() {
    return new DirectChannel();
  }

  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
//  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel", defaultRequestTimeout = "50000", defaultReplyTimeout = "5000")
  public interface RabbitGateway {
    void sendToRabbit(String data);
  }

}
