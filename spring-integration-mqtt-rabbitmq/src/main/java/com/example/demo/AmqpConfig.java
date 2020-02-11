package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Slf4j
@Configuration
public class AmqpConfig {

  public final static String QUEUE_MQTT = "mqtt";

  @Autowired
  private MqttHandler mqttHandler;

  @Bean
  public Queue mqttQueue() {
    return new Queue(QUEUE_MQTT);
  }

  @Bean
  public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
    return IntegrationFlows.from(Amqp.inboundAdapter(connectionFactory, QUEUE_MQTT))
        .handle(mqttHandler)
        .get();
  }

  public void handle(String message) {
    log.debug(message);
  }
}
