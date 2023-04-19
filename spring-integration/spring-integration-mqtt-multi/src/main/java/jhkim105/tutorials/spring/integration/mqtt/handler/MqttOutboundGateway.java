package jhkim105.tutorials.spring.integration.mqtt.handler;


import static jhkim105.tutorials.spring.integration.mqtt.config.ChannelConfig.MQTT_OUTBOUND_CHANNEL;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = MQTT_OUTBOUND_CHANNEL)
public interface MqttOutboundGateway {
  @Gateway
  void publish(@Header(MqttMultiMessageHandler.MQTT_OUTBOUND_BROKER_URL) String brokerUrl,
      @Header(MqttHeaders.TOPIC) String topic, String message);


}