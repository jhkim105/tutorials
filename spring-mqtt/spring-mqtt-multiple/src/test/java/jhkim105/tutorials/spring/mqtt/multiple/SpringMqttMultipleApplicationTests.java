package jhkim105.tutorials.spring.mqtt.multiple;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

@SpringBootTest
class SpringMqttMultipleApplicationTests {

  @Autowired(required = false)
  private MqttUtils mqttUtils;

  @Autowired
  private IntegrationFlowContext integrationFlowContext;

  @Test
  void publish() {
    mqttUtils.publish("/test2", "aaaa");
  }

  @Test
  void publish2() {
//    integrationFlowContext.getRegistry().values().forEach(o -> o.getIntegrationFlow().);
  }


}
