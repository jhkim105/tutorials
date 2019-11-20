package com.example.demo;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

import java.util.UUID;

@Configuration
@IntegrationComponentScan
public class MqttIntegrationConfig {

  private static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";

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
    return factory;
  }

  @Bean(name = MQTT_OUTBOUND_CHANNEL)
  public MessageChannel outboundChannel() {
    DirectChannel channel = new DirectChannel();
    return channel;
  }

  @Bean
  public IntegrationFlow outboundFlow() {
    return IntegrationFlows.from(outboundChannel()).handle(outboundMessageHandler()).get();
  }

  public MessageHandler outboundMessageHandler() {
    String clientId = UUID.randomUUID().toString();
    MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
    messageHandler.setAsync(mqttProperties.isAsync());
    messageHandler.setDefaultTopic("defaultTopic");
    messageHandler.setDefaultQos(mqttProperties.getQos());
    messageHandler.setCompletionTimeout(5000);
    return messageHandler;
  }

  @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL, defaultRequestTimeout = "5000", defaultReplyTimeout = "5000")
  public interface OutboundGateway {
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, String data);
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
  }

}
