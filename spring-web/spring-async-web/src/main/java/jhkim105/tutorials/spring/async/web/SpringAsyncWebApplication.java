package jhkim105.tutorials.spring.async.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringAsyncWebApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAsyncWebApplication.class, args);
  }

}
