package jhkim105.tutorials.multitenancy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringDataMultitenancyApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataMultitenancyApplication.class, args);
  }

}
