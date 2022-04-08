package jhkim105.tutorials.spring.integration.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MqttProperties.class})
public class SpringIntegrationMqttApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringIntegrationMqttApplication.class, args);
  }

}
