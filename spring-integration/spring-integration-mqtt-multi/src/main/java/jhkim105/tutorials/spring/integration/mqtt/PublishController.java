package jhkim105.tutorials.spring.integration.mqtt;

import jhkim105.tutorials.spring.integration.mqtt.config.MqttProperties;
import jhkim105.tutorials.spring.integration.mqtt.handler.MqttOutboundGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PublishController {

  private final MqttProperties mqttProperties;
  private final MqttOutboundGateway mqttOutboundGateway;

  private final MqttClients mqttClients;
  @PostMapping("/publish")
  public void publish(String message) {
    mqttOutboundGateway.publish(mqttProperties.getBrokerUrl()[0], "test", message);
  }

  @PostMapping("/publish2")
  public void publish2(String message) {
    mqttClients.publish(mqttProperties.getBrokerUrl()[0], "test", message);
  }
}
