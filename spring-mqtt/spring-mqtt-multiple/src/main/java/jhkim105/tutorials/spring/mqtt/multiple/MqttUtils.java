package jhkim105.tutorials.spring.mqtt.multiple;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttUtils {

  private final MqttOutboundGateway outboundGateway;

  private final MqttConfig mqttConfig;

  public void publish(String topic, String payload) {
    outboundGateway.publish(topic, payload);
  }

  public void reloadMqttServers() {
    mqttConfig.removeIntegrationFlows();
    mqttConfig.createInboundFlow();
    mqttConfig.createOutboundFlow();
  }

}
