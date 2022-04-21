package jhkim105.tutorials.config.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ConfigClientApplication implements ApplicationRunner {

  public static void main(String[] args) {
    SpringApplication.run(ConfigClientApplication.class, args);
  }

  private final Environment environment;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("app.config.name:{}", environment.getProperty("app.config.name"));
    log.info("app.config.password:{}", environment.getProperty("app.config.password"));
  }
}
