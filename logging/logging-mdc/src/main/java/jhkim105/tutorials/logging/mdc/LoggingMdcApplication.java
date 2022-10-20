package jhkim105.tutorials.logging.mdc;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LoggingMdcApplication implements ApplicationRunner {

  public static void main(String[] args) {
    log.debug("start");
    SpringApplication.run(LoggingMdcApplication.class, args);
  }


  @Override
  public void run(ApplicationArguments args) throws Exception {
    doLoggingMdc();

  }

  private void doLoggingMdc() {
    log.debug("mdc test start");
    MDC.put("userId", "xxxx-yyyyy");
    log.debug("after mdc.put()");
    MDC.clear();
    log.debug("after mdc clear");
  }
}
