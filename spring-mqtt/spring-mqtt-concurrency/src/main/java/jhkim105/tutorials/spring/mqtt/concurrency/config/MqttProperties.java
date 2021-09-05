package jhkim105.tutorials.spring.mqtt.concurrency.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("mqtt")
public class MqttProperties {

  private String brokerUrl;
  private int qos;
  private int completionTimeout; //ms
  private boolean async;
}
