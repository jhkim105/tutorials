package jhkim105.tutorials.spring.mqtt.multiple;

import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;

@Configuration
@IntegrationComponentScan
@Slf4j
@RequiredArgsConstructor
public class MqttConfig {

  private static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";
  private final MqttProperties mqttProperties;

  private final IntegrationFlowContext integrationFlowContext;

  private static final String TOPIC = "/test";

  private static final String MQTT_LOGGING_CHANNEL = "mqttLoggingChannel";

  private static final String INBOUND_PREFIX = "inbound";
  private static final String OUTBOUND_PREFIX = "outbound";



  @PostConstruct
  public void init() {
    createInboundFlow();
    createOutboundFlow();
  }

  private void createInboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    for(String url : brokerUrls) {
      integrationFlowContext.registration(mqttInboundFlow(url)).id(String.format("%s-%s", INBOUND_PREFIX, url.replace(":", ""))).register();
    }
  }

  private IntegrationFlow mqttInboundFlow(String brokerUrl) {
    String clientId = UUID.randomUUID().toString();
    return IntegrationFlows
        .from(mqttInboundAdapter(mqttClientFactory(brokerUrl), clientId, TOPIC))
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(this::handle)
        .get();
  }

  private MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(MqttPahoClientFactory mqttClientFactory, String clientId, String topic) {
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory, topic);
    adapter.setCompletionTimeout(mqttProperties.getCompletionTimeout());
    adapter.setConverter(new DefaultPahoMessageConverter());
    adapter.setQos(mqttProperties.getQos());
    return adapter;
  }


  private void handle(Message<?> message) {
    log.info("message->{}", message);
  }


  private void createOutboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    for(String url : brokerUrls) {
      integrationFlowContext.registration(mqttOutboundFlow(url)).id(String.format("%s-%s", OUTBOUND_PREFIX, url.replace(":", ""))).register();
    }
  }

  @Bean(name = MQTT_OUTBOUND_CHANNEL)
  public MessageChannel outboundChannel() {
    DirectChannel channel = new DirectChannel();
    return channel;
  }

  private IntegrationFlow mqttOutboundFlow(String brokerUrl) {
    return IntegrationFlows
        .from(outboundChannel())
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(outboundMessageHandler(brokerUrl)).get();
  }

  public MessageHandler outboundMessageHandler(String brokerUrl) {
    String clientId = UUID.randomUUID().toString();
    MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory(brokerUrl));
    messageHandler.setAsync(mqttProperties.isAsync());
    messageHandler.setDefaultTopic("test2");
    messageHandler.setDefaultQos(mqttProperties.getQos());
    messageHandler.setCompletionTimeout(mqttProperties.getCompletionTimeout());
    return messageHandler;
  }

  @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL, defaultRequestTimeout = "5000", defaultReplyTimeout = "5000")
  public interface OutboundGateway {
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, String data);
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
  }

  private MqttPahoClientFactory mqttClientFactory(String brokerUrl) {
    log.info("mqttProperties->{}", mqttProperties);
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(new String[]{brokerUrl});
    factory.setConnectionOptions(options);
    return factory;
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
    loggingHandler.setLoggerName("mqttLogger");
    return loggingHandler;
  }

}
