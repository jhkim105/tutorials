package jhkim105.tutorials.spring.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


// ApplicationEvent 상속받지 않아도 정상 동작함. 차이는?
@Getter
public class CustomApplicationEvent extends ApplicationEvent {
    private final String message;
    private final boolean success;

    public CustomApplicationEvent(Object source, String message) {
        super(source);
        this.message = message;
        this.success = true;
    }

}