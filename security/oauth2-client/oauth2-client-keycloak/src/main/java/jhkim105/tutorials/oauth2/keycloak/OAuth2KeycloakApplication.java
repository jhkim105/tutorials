package jhkim105.tutorials.oauth2.keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OAuth2KeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(OAuth2KeycloakApplication.class, args);
  }


  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

}
