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
class MyEventTest {

  @Autowired
  private ApplicationEvents applicationEvents;

  @Autowired
  private EventPublisher publisher;

  @SpyBean
  private MyEventListener myEventListener;

  @Test
  void publishEvent() {
    publisher.publishMyEvent("Hello world!!");
    log.info("Published MyEvent.");
    assertThat(applicationEvents.stream(MyEvent.class).count()).isEqualTo(1);
    then(myEventListener).should(times(1)).handleEvent(any());
  }

}