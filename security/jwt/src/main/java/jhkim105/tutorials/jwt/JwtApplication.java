package jhkim105.tutorials.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JwtApplication {

  public static void main(String[] args) {
    SpringApplication.run(JwtApplication.class, args);
  }

}
