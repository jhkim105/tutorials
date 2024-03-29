package jhkim105.tutorials.spring_integration_mqtt_rabbitmq;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringIntegrationMqttRabbitmqApplicationTests {

  @Autowired
  private MqttHandler mqttHandler;

  @Test
  public void test() {
    mqttHandler.handle("a");
    mqttHandler.handle("a");
    mqttHandler.handle("a");
  }

}
