package jhkim105.tutorials.spring.mqtt.multiple;

import static jhkim105.tutorials.spring.mqtt.multiple.MqttConfig.MQTT_OUTBOUND_CHANNEL;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
public interface MqttOutboundGateway {

  @Gateway
  void publish(@Header(MqttHeaders.TOPIC) String topic, String data);

  @Gateway
  void publish(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) Integer qos, String data);
}