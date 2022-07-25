package jhkim105.tutorials.spring.events.simple;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;


public class CustomSpringEvent extends ApplicationEvent {
    private String message;
    @Getter
    private boolean success;

    public CustomSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
        this.success = true;
    }
    public String getMessage() {
        return message;
    }
}