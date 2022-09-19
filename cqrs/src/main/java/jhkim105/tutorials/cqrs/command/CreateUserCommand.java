package jhkim105.tutorials.cqrs.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserCommand {

  private String name;

}
