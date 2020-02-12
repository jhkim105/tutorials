package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

import java.util.UUID;

@Configuration
@IntegrationComponentScan
@Slf4j
public class MqttConfig {

  private static final String MQTT_LOGGING_CHANNEL = "mqttLoggingChannel";

  @Autowired
  private MqttProperties mqttProperties;

  @Bean
  public MqttPahoClientFactory mqttClientFactory() {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(new String[]{mqttProperties.getBrokerUrl()});
//    options.setUserName("username");
//    options.setPassword("password".toCharArray());
    factory.setConnectionOptions(options);
    log.debug("brokerUrl:{}", mqttProperties.getBrokerUrl());
    return factory;
  }


  @Bean
  public IntegrationFlow mqttInboundFlow(RabbitTemplate rabbitTemplate) {
    String clientId = UUID.randomUUID().toString();

    return IntegrationFlows.from(mqttInboundAdapter(clientId, "/test"))
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(Amqp.outboundAdapter(rabbitTemplate).routingKey(AmqpConfig.QUEUE_MQTT)).get();
  }

  @Bean
  public IntegrationFlow mqttInboundFlow2(RabbitTemplate rabbitTemplate) {
    String clientId = UUID.randomUUID().toString();

    return IntegrationFlows.from(mqttInboundAdapter(clientId, "/test"))
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(Amqp.outboundAdapter(rabbitTemplate).routingKey(AmqpConfig.QUEUE_MQTT)).get();
  }

  private MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(String clientId, String topic) {
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory(), topic);
    adapter.setCompletionTimeout(mqttProperties.getCompletionTimeout());
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(mqttProperties.getQos());
    return adapter;
  }



  @Bean(name = MQTT_LOGGING_CHANNEL)
  public MessageChannel loggingChannel() {
    return MessageChannels.direct().get();
  }

  @Bean
  public IntegrationFlow loggingFlow() {
    return IntegrationFlows.from(MQTT_LOGGING_CHANNEL).handle(loggingHandler()).get();
  }

  @Bean
  public LoggingHandler loggingHandler() {
    LoggingHandler loggingHandler = new LoggingHandler(LoggingHandler.Level.INFO.name());
    loggingHandler.setShouldLogFullMessage(true);
    loggingHandler.setLoggerName("mqttLogger"); // logback 의 logger name 과 맞추면 해당 로거를 사용해서 로깅한다.
    return loggingHandler;
  }
}
