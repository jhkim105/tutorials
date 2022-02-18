package jhkim105.tutorials.config.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

  private final Environment environment;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    log.info("config.name:{}", environment.getProperty("config.name"));
  }
}
