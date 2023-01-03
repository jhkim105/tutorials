package jhkim105.tutorials.config.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Slf4j
public class ConfigClientApplication  {

  public static void main(String[] args) {
    SpringApplication.run(ConfigClientApplication.class, args);
  }

  private final Environment environment;

  @Bean
  public ApplicationRunner run(AppProperties appProperties) throws Exception {
    return (arguments) -> {
      log.info("app.name: {}", environment.getProperty("app.name"));
      log.info("app.config-name: {}", environment.getProperty("app.config-name"));
      log.info("app.config-password: {}", environment.getProperty("app.config-password"));
      log.info("appProperties: {}", appProperties);
    };
  }
}
