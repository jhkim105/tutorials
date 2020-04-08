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
  @ServiceActivator(inputChannel = "amqpOutboundChannel")
  public AmqpOutboundEndpoint amqpOutbound(AmqpTemplate amqpTemplate) {
    AmqpOutboundEndpoint outbound = new AmqpOutboundEndpoint(amqpTemplate);
    outbound.setExpectReply(true);
    outbound.setRoutingKey("foo"); // default exchange - route to queue 'foo'
    return outbound;
  }


  @Bean
  public MessageChannel amqpOutboundChannel() {
    return new DirectChannel();
  }


  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
//  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel", defaultRequestTimeout = "50000", defaultReplyTimeout = "5000")
  public interface RabbitGateway {
    String sendToRabbit(String data);
  }

}
