package jhkim105.tutorials.spring.events;

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
class CustomApplicationEventTest {

  @Autowired
  private ApplicationEvents applicationEvents;

  @Autowired
  private EventPublisher publisher;

  @SpyBean
  private CustomApplicationEventListener customApplicationEventListener;

  @Test
  void publishEvent() {
    publisher.publishCustomApplicationEvent("Hello world!!");
    log.info("Done publishing synchronous custom event.");
    assertThat(applicationEvents.stream(CustomApplicationEvent.class).count()).isEqualTo(1);
    then(customApplicationEventListener).should(times(1)).onApplicationEvent(any());
  }

}