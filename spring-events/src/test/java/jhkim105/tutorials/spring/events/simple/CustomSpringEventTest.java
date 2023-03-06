package jhkim105.tutorials.spring.events.simple;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
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

  @SpyBean
  private CustomSpringEventListener customSpringEventListener;

  @Test
  void event() {
    publisher.publishCustomEvent("Hello world!!");
    log.info("Done publishing synchronous custom event.");
    assertThat(applicationEvents.stream(CustomSpringEvent.class).count()).isEqualTo(1);
    then(customSpringEventListener).should(times(1)).onApplicationEvent(any());
  }

}