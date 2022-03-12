package jhkim105.tutorials.spring.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SampleService {
  public void doSomething() {
    log.info("doSomething start.");
  }
}
