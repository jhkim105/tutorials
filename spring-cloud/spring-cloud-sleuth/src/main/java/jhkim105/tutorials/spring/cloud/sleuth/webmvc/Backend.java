package jhkim105.tutorials.spring.cloud.sleuth.webmvc;


import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@RestController
@Slf4j
public class Backend {

  @RequestMapping("/api")
  public String api(@RequestHeader(name = "username", required = false) String username) {
    log.info("Backend:{}", username);
    if (username != null) {
      return new Date() + " " + username;
    }
    return new Date().toString();
  }

  public static void main(String[] args) {
    SpringApplication.run(Backend.class,
        "--spring.application.name=backend",
        "--server.port=9000"
    );
  }
}
