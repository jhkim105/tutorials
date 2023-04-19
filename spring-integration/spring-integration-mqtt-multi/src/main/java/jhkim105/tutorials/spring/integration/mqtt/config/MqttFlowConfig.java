package jhkim105.tutorials.spring.integration.mqtt.config;

import static jhkim105.tutorials.spring.integration.mqtt.config.ChannelConfig.MQTT_LOGGING_CHANNEL;
import static jhkim105.tutorials.spring.integration.mqtt.config.ChannelConfig.MQTT_OUTBOUND_CHANNEL;

import java.util.Arrays;
import java.util.UUID;
import jhkim105.tutorials.spring.integration.mqtt.handler.MqttInboundHandler;
import jhkim105.tutorials.spring.integration.mqtt.handler.MqttMultiMessageHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class MqttFlowConfig implements InitializingBean {

  private static final String TOPIC = "test";
  private static final String INBOUND_PREFIX = "inbound";
  private static final String OUTBOUND_PREFIX = "outbound";


  private final MqttProperties mqttProperties;
  private final IntegrationFlowContext integrationFlowContext;
  private final MqttInboundHandler mqttInboundHandler;

  @Override
  public void afterPropertiesSet() throws Exception {
    createInboundFlow();
    createOutboundFlow();
  }

  private void createInboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    for(String url : brokerUrls) {
      integrationFlowContext
          .registration(mqttInboundFlow(url))
          .id(String.format("%s-%s", INBOUND_PREFIX, url.replace(":", "")))
          .register();
    }
  }
  private IntegrationFlow mqttInboundFlow(String brokerUrl) {
    String clientId = UUID.randomUUID().toString();
    return IntegrationFlows
        .from(mqttInboundAdapter(mqttClientFactory(brokerUrl), clientId, TOPIC))
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(mqttInboundHandler)
        .get();
  }

  private MqttPahoMessageDrivenChannelAdapter mqttInboundAdapter(MqttPahoClientFactory mqttClientFactory, String clientId, String topic) {
    MqttPahoMessageDrivenChannelAdapter adapter =
        new MqttPahoMessageDrivenChannelAdapter(clientId, mqttClientFactory, topic);
    adapter.setConverter(new DefaultPahoMessageConverter());
    return adapter;
  }

  private void createOutboundFlow() {
    String[] brokerUrls = mqttProperties.getBrokerUrl();
    IntegrationFlow mqttOutboundFlow = IntegrationFlows
        .from(MQTT_OUTBOUND_CHANNEL)
        .wireTap(MQTT_LOGGING_CHANNEL)
        .handle(new MqttMultiMessageHandler(Arrays.asList(brokerUrls), mqttClientFactory(null)))
        .get();
    integrationFlowContext
        .registration(mqttOutboundFlow)
        .id(String.format("%s-%s", OUTBOUND_PREFIX, brokerUrls[0].replace(":", "")))
        .register();
  }

  private MqttPahoClientFactory mqttClientFactory(String brokerUrl) {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    if (brokerUrl != null) {
      options.setServerURIs(new String[]{brokerUrl});
    }
    factory.setConnectionOptions(options);
    return factory;
  }



}
