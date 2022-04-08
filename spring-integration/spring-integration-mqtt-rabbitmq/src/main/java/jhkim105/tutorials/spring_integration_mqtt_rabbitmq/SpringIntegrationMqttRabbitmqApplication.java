package jhkim105.tutorials.spring_integration_mqtt_rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({MqttProperties.class})
public class SpringIntegrationMqttRabbitmqApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringIntegrationMqttRabbitmqApplication.class, args);
  }

}
