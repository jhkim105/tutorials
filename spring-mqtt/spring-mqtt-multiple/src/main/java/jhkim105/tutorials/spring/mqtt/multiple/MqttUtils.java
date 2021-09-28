package jhkim105.tutorials.spring.mqtt.multiple;

import java.util.Collection;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MqttUtils {

  @Resource(name = "outboundMessageHandlers")
  private Collection<MqttPahoMessageHandler> outboundMessageHandlers;

  private final MqttProperties mqttProperties;

  public void publish(String topic, String payload) {
    Message<String> message = MessageBuilder
        .withPayload(payload)
        .setHeader(MqttHeaders.TOPIC, topic)
        .setHeader(MqttHeaders.QOS, mqttProperties.getQos())
        .build();

    outboundMessageHandlers.parallelStream().forEach(messageHandler -> messageHandler.handleMessage(message));
  }

}
