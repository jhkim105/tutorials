package jhkim105.tutorials.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {

  private String id;
  private String username;

}
