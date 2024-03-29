package jhkim105.tutorials.spring.cloud.openfeign;

import feign.Logger;
import feign.Logger.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class SpringCloudOpenfeignApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudOpenfeignApplication.class, args);
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.BASIC;
  }

}
