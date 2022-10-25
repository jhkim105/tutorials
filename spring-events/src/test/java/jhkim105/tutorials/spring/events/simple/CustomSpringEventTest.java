package jhkim105.tutorials.spring.events.simple;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;


@SpringBootTest
@RecordApplicationEvents
@Slf4j
class CustomSpringEventTest {

  @Autowired
  private ApplicationEvents applicationEvents;
  @Autowired
  private CustomSpringEventPublisher publisher;

  @Test
  void event() {
    publisher.publishCustomEvent("Hello world!!");
    log.info("Done publishing synchronous custom event.");
    assertThat(applicationEvents.stream(CustomSpringEvent.class).count()).isEqualTo(1);

  }

}