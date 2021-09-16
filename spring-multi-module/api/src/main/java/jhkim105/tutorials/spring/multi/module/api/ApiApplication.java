package jhkim105.tutorials.spring.multi.module.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"jhkim105.tutorials.spring.multi.module.core", "jhkim105.tutorials.spring.multi.module.api"})
@EnableConfigurationProperties({ApiProperties.class})
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }

}
