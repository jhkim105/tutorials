package jhkim105.tutorials.spring.cloud.sleuth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class  SpringCloudSleuthApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudSleuthApplication.class, args);
  }

}
