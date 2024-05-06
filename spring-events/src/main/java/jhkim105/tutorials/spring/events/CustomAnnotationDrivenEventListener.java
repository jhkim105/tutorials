package jhkim105.tutorials.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class CustomAnnotationDrivenEventListener {

  @EventListener
  @Async
  public void handleEvent(final CustomSpringEvent event) {
    log.info("handleEvent: {}", event.getMessage());
  }

  @EventListener(CustomSpringEvent.class)
  public void handleEvent() {
    log.info("handleEvent");
  }

  @EventListener(condition = "#event.success")
  public void handleEventByCondition(final CustomSpringEvent event) {
    log.info("handleEventByCondition: " + event.getMessage());
  }

  @TransactionalEventListener
//  @Async
  public void handleTransactionalEvent(final CustomSpringEvent springEvent) {
    log.info("handleTransactionalEvent: {}", springEvent);
  }
}