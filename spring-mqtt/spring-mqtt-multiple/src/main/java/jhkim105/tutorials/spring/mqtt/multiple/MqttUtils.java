package jhkim105.tutorials.spring.mqtt.multiple;

import jhkim105.tutorials.spring.mqtt.multiple.MqttConfig.OutboundGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttUtils {

  @Autowired(required = false)
  private OutboundGateway outboundGateway;

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
