package jhkim105.tutorials.spring.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringAopApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAopApplication.class, args);
  }

}
