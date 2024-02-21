package jhkim105.tutorials.spring.mvc;

import jhkim105.tutorials.spring.mvc.config.ServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(ServiceProperties.class)
@ServletComponentScan
public class SpringMvcApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringMvcApplication.class, args);
  }

  @Bean
  public ApplicationRunner loggingProperties(ServiceProperties serviceProperties) {
    return (args) -> {
      log.info("{}", serviceProperties);
    };

  }

}
