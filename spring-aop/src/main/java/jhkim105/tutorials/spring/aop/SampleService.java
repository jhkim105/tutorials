package jhkim105.tutorials.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {

  @MyAnnotation(key = "#sample.id")
  public void process(Sample sample) throws InterruptedException {
    log.info("process executed");
  }

  @MyAnnotation(key = "#userId")
  public void process(String userId) throws InterruptedException {
    log.info("process executed");
  }

  @MyAnnotation(key = "#userId")
  @Async
  public void processAsync(String userId) throws InterruptedException {
    log.info("processAsync executed");
  }

}
