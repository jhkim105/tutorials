package jhkim105.tutorials.redisson;

import jhkim105.tutorials.redisson.service.CurrentDate;
import jhkim105.tutorials.redisson.service.CurrentDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RequiredArgsConstructor
@RestController
public class SpringCacheRedissonApplication {

  private final CurrentDateService currentDateService;
  public static void main(String[] args) {
    SpringApplication.run(SpringCacheRedissonApplication.class, args);
  }

  @GetMapping("/")
  public CurrentDate get() {
    return currentDateService.getCurrentDate("mm.ss.SSS");
  }

  
}
