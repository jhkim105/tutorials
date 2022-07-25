package jhkim105.tutorials.spring.events.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


@SpringBootTest
@Slf4j
class CustomSpringEventTest {

  @Autowired
  private CustomSpringEventPublisher publisher;

  @Test
  void event() {
    publisher.publishCustomEvent("Hello world!!");
    log.info("Done publishing synchronous custom event.");
  }

}