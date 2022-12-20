package jhkim105.tutorials.guava.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GuavaCacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(GuavaCacheApplication.class, args);
  }

}
