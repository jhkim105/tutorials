package jhkim105.tutorials.spring.integration.mqtt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jhkim105.tutorials.spring.integration.mqtt.handler.MqttOutboundGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MqttOutboundGatewayTest {

  @Autowired(required = false)
  private MqttOutboundGateway mqttOutboundGateway;

  @Test
  void send() {
    String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss SSS"));
    log.info(time);
    mqttOutboundGateway.publish("tcp://devpns.remotemeeting.com:1883", "test", time);
  }
}
