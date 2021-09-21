package jhkim105.tutorials.spring.mqtt.multiple;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Component;

@Component
public class MqttConfigUtils {
  public MqttPahoMessageHandler outboundMessageHandler(MqttProperties mqttProperties, String brokerUrl) {
    String clientId = UUID.randomUUID().toString();
    MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(clientId, mqttClientFactory(brokerUrl));
    messageHandler.setAsync(mqttProperties.isAsync());
    messageHandler.setDefaultQos(mqttProperties.getQos());
    messageHandler.setCompletionTimeout(mqttProperties.getCompletionTimeout());
    messageHandler.afterPropertiesSet();
    return messageHandler;
  }

  public MqttPahoClientFactory mqttClientFactory(String brokerUrl) {
    DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
    MqttConnectOptions options = new MqttConnectOptions();
    options.setServerURIs(new String[]{brokerUrl});
    factory.setConnectionOptions(options);
    return factory;
  }
}
