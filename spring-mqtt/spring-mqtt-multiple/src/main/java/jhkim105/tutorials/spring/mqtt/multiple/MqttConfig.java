package jhkim105.tutorials.spring.mqtt.multiple;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StringUtils;

@Configuration
@IntegrationComponentScan
@Slf4j
@RequiredArgsConstructor
public class MqttConfig {

  private static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";
  private final MqttProperties mqttProperties;

  private final IntegrationFlowContext integrationFlowContext;

  private final MqttConfigUtils mqttConfigUtils;

  private static final String TOPIC = "/test";

  private static final String MQTT_LOGGING_CHANNEL = "mqttLoggingChannel";

  private static final String INBOUND_PREFIX = "inbound";
  private static final String OUTBOUND_PREFIX = "outbound";



  @PostConstruct
  public void init() {
    createInboundFlow();
    createOutboundFlow();
  }

  public void createInboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    for(String url : brokerUrls) {
      integrationFlowContext.registration(mqttInboundFlow(url)).id(String.format("%s-%s", INBOUND_PREFIX, url.replace(":", ""))).register();
    }
  }

  private IntegrationFlow mqttInboundFlow(String brokerUrl) {
    String clientId = UUID.randomUUID().toString();
    return IntegrationFlows
        .from(mqttInboundAdapter(mqttConfigUtils.mqttClientFactory(brokerUrl), clientId, TOPIC))
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


  public void createOutboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    for(String url : brokerUrls) {
      integrationFlowContext.registration(mqttOutboundFlow(url))
          .id(String.format("%s-%s", OUTBOUND_PREFIX, url.replace(":", ""))).register();
    }
  }

  private IntegrationFlow mqttOutboundFlow(String url) {
    return IntegrationFlows
        .from(outboundChannel())
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(mqttConfigUtils.outboundMessageHandler(mqttProperties, url)).get();
  }


  @Bean(name = MQTT_OUTBOUND_CHANNEL)
  public MessageChannel outboundChannel() {
    PublishSubscribeChannel channel = new PublishSubscribeChannel();
    return channel;
  }

  @MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
  public interface OutboundGateway {
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, String data);
    @Gateway
    void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
  }

  public void removeIntegrationFlows() {
    List<String> targetIds = integrationFlowContext.getRegistry().keySet().stream()
        .filter(this::isMqttFlowId)
        .collect(toList());

    targetIds.forEach(integrationFlowContext::remove);
  }

  private boolean isMqttFlowId(String flowId) {
    return StringUtils.startsWithIgnoreCase(flowId, INBOUND_PREFIX) || StringUtils.startsWithIgnoreCase(flowId, OUTBOUND_PREFIX);
  }

  private void removeInboundFlows() {

  }

  private void removeOutboundFlows() {

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
