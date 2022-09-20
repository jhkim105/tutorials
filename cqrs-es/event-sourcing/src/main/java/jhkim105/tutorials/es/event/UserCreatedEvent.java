package jhkim105.tutorials.es.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserCreatedEvent extends Event {

    private String name;

}