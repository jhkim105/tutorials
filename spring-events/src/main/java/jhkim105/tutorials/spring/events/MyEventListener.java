package jhkim105.tutorials.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyEventListener {

 @EventListener
  public void handleEvent(MyEvent myEvent) {
    log.info("handleEvent: {}", myEvent);
  }

}