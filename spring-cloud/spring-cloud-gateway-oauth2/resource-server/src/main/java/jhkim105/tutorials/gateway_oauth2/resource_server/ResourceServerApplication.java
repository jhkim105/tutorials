package jhkim105.tutorials.gateway_oauth2.resource_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@EnableWebFluxSecurity
public class ResourceServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ResourceServerApplication.class, args);
  }

}
