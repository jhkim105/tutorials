package jhkim105.tutorials.spring.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomApplicationEvent(final String message) {
        log.info("Publishing customApplicationEvent. ");
        var customApplicationEvent = new CustomApplicationEvent(this, message);
        applicationEventPublisher.publishEvent(customApplicationEvent);
    }

    public void publishMyEvent(final String message) {
        log.info("Publishing myEvent. {}", message);
        var customEvent = new MyEvent(message);
        applicationEventPublisher.publishEvent(customEvent);
    }
}