package jhkim105.tutorials.spring.integration.mqtt.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties("mqtt")
public class MqttProperties {

  private String[] brokerUrl;

}
