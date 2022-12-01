package jhkim105.tutorials.spring.mvc;

import jhkim105.tutorials.spring.mvc.config.AppProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@ConfigurationPropertiesScan
public class SpringMvcApplication implements ApplicationRunner {

  private final AppProperties appProperties;


  public static void main(String[] args) {
    SpringApplication.run(SpringMvcApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("{}", appProperties);
  }

}
