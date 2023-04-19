package jhkim105.tutorials.spring.integration.mqtt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfig {

  public static final String MQTT_OUTBOUND_CHANNEL = "outboundChannel";
  public static final String MQTT_LOGGING_CHANNEL = "mqttLoggingChannel";
  @Bean(name = MQTT_OUTBOUND_CHANNEL)
  public MessageChannel outboundChannel() {
    return new DirectChannel();
  }

  @Bean(name = MQTT_LOGGING_CHANNEL)
  public MessageChannel loggingChannel() {
    return MessageChannels.direct().get();
  }
}
