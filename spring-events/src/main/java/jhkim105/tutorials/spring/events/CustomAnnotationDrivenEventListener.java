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
  public void handleEvent(final CustomApplicationEvent event) {
    log.info("handleEvent: {}", event.getMessage());
  }

  @EventListener(CustomApplicationEvent.class)
  public void handleEvent() {
    log.info("handleEvent");
  }

  @EventListener(condition = "#event.success")
  public void handleEventByCondition(final CustomApplicationEvent event) {
    log.info("handleEventByCondition: " + event.getMessage());
  }

  @TransactionalEventListener
//  @Async
  public void handleTransactionalEvent(final CustomApplicationEvent springEvent) {
    log.info("handleTransactionalEvent: {}", springEvent);
  }
}