package jhkim105.tutorials.es.event;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatedEvent extends Event {

    private String userId;
    private String username;

}