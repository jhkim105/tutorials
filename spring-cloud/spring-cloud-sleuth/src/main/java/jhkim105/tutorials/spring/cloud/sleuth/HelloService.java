package jhkim105.tutorials.spring.cloud.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class HelloService {

  public void doSomething() throws InterruptedException {
    Thread.sleep(1000);
    log.debug("doSomething");

  }

  @Async
  public void doSomethingAsync() throws InterruptedException {
    log.info("Start");
    Thread.sleep(1000L);
    log.info("End");
  }
}
