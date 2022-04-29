package jhkim105.tutorials.spring.jasypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringJasyptApplication implements ApplicationRunner {

  private final AppProperties appProperties;

  public static void main(String[] args) {
    SpringApplication.run(SpringJasyptApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("{}", appProperties);
  }
}
