package jhkim105.tutorials.cqrs.command;

import java.util.Set;
import jhkim105.tutorials.cqrs.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserCommand {

  private String id;
  private Set<Address> addresses;
}
