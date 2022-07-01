package jhkim105.tutorials.multitenancy;

import jhkim105.tutorials.multitenancy.master.service.TenantService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class SpringDataMultitenancyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataMultitenancyApplication.class, args);
  }

  @Profile("!test")
  @Bean
  public ApplicationRunner tenantInitialize(TenantService tenantService) {
    return args -> {
      tenantService.createTenant("user1");
    };

  }

}