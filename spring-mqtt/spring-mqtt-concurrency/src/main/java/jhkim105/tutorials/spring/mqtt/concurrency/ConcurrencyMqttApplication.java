package jhkim105.tutorials.spring.mqtt.concurrency;

import jhkim105.tutorials.spring.mqtt.concurrency.config.MqttProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MqttProperties.class})
public class ConcurrencyMqttApplication {

  public static void main(String[] args) {
    SpringApplication.run(ConcurrencyMqttApplication.class, args);
  }

}
