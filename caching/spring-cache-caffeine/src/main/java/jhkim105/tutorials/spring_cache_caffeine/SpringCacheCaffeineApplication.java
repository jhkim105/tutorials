package jhkim105.tutorials.spring_cache_caffeine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringCacheCaffeineApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCacheCaffeineApplication.class, args);
  }

}
