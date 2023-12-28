package jhkim105.tutorials.spring.events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringEventsApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringEventsApplication.class, args);
  }

}
