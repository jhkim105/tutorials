package jhkim105.tutorials.spring.integration.mqtt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringIntegrationMqttMultiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringIntegrationMqttMultiApplication.class, args);
  }


}
