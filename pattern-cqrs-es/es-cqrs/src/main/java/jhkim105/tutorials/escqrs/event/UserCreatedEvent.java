package jhkim105.tutorials.escqrs.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent extends Event {

    private String userId;
    private String username;

}