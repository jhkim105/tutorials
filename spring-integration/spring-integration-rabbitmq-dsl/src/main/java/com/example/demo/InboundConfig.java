package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@Slf4j
public class InboundConfig {

//  @Bean // return the upper cased payload
//  public IntegrationFlow amqpInboundGateway(ConnectionFactory connectionFactory) {
//    return IntegrationFlows.from(Amqp.inboundGateway(connectionFactory, "foo"))
//        .transform(String.class, String::toUpperCase)
//        .handle(this, "handle")
//        .get();
//  }

  @Bean
  public IntegrationFlow routeFlow(ConnectionFactory connectionFactory) {
    return IntegrationFlows
        .from(Amqp.inboundAdapter(connectionFactory, "foo"))
        .transform(String.class, String::toUpperCase)
        .handle(this, "handle")
        .split()
        .<String, Boolean>route(s -> s.equals("A"),
            m -> m
                .subFlowMapping(true, handle1Flow())
                .subFlowMapping(false, handle2Flow())).get();
  }

  @Bean
  public IntegrationFlow handle1Flow() {
    return f -> f.handle(m -> log.debug("handle1"));
  }

  @Bean
  public IntegrationFlow handle2Flow() {
    return f -> f.handle(m -> log.debug("handle2"));
  }

  public String handle(String message) {
    log.debug("message:{}", message);
    return message;
  }


  public String handleAndReply(String message) {
    log.debug("message:{}", message);
    return String.format("reply to %s", message);
  }

}
