package jhkim105.tutorials.cqrs.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressByNameQuery {

  private String userId;
  private String name;

}
