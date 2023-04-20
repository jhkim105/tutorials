package jhkim105.tutorials.spring.integration.mqtt;

import jhkim105.tutorials.spring.integration.mqtt.config.MqttProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqttClientsTests {

  @Autowired
  MqttClients mqttClients;

  @Autowired
  MqttProperties mqttProperties;

  @Test
  void publish() {
    mqttClients.publish(mqttProperties.getBrokerUrl()[0], "test", "test message");
  }

}
