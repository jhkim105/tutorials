package jhkim105.tutorials.redis;

import jhkim105.tutorials.redis.service.CurrentDate;
import jhkim105.tutorials.redis.service.CurrentDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
public class SpringCacheRedisApplication {

  private final CurrentDateService currentDateService;
  public static void main(String[] args) {
    SpringApplication.run(SpringCacheRedisApplication.class, args);
  }

  @GetMapping("/")
  public CurrentDate get() {
    return currentDateService.getCurrentDate("mm.ss.SSS");
  }

}
