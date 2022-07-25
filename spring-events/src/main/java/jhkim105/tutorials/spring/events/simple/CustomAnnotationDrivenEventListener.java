package jhkim105.tutorials.spring.events.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomAnnotationDrivenEventListener {
    @EventListener
    public void handleEvent(final CustomSpringEvent event) {
        log.info("handleEvent: {}", event.getMessage());
    }

    @EventListener(condition = "#event.success")
    public void handleEventByCondition(final CustomSpringEvent event) {
        log.info("handleEventByCondition: " + event.getMessage());
    }

}