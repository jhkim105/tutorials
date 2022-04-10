package jhkim105.tutorials.spring.integration.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttInboundHandler {

  @ServiceActivator
  public void handle(Message message) {
    log.info("{}", message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC, String.class));
  }

}
