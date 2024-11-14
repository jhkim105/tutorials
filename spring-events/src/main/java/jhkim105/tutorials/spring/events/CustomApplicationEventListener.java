package jhkim105.tutorials.spring.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomApplicationEventListener implements ApplicationListener<CustomApplicationEvent> {
    @Override
    public void onApplicationEvent(CustomApplicationEvent event) {
        log.info("Received spring custom event - {}", event.getMessage());
    }
}