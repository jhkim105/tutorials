package com.example.demo;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@IntegrationComponentScan
@Slf4j
public class MqttIntegrationConfig {

  private static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";

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
    return factory;
  }

  @Bean(name = MQTT_OUTBOUND_CHANNEL)
  public MessageChannel outboundChannel() {
    DirectChannel channel = new DirectChannel();
    return channel;
  }

  @Bean
  public IntegrationFlow outboundFlow() {
    return IntegrationFlows
        .from(outboundChannel())
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(outboundMessageHandler()).get();
  }

  public MqttPahoMessageHandler outboundMessageHandler() {
    String clientId = UUID.randomUUID().toString();
    MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory());
    messageHandler.setAsync(mqttProperties.isAsync());
    messageHandler.setDefaultTopic("defaultTopic");
    messageHandler.setDefaultQos(mqttProperties.getQos());
    messageHandler.setCompletionTimeout(5000);
    return messageHandler;
  }

//  @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL, defaultRequestTimeout = "5000", defaultReplyTimeout = "5000")
  @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
  public interface OutboundGateway {
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, String data);
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
  }


  @Bean
  public IntegrationFlow inboundFlow() {
    String clientId = UUID.randomUUID().toString();

    return IntegrationFlows.from(
        inboundAdapter(clientId, "/test"))
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(m -> log.debug("message -> {}", m))
        .get();
  }


  private MqttPahoMessageDrivenChannelAdapter inboundAdapter(String clientId, String topic) {
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
