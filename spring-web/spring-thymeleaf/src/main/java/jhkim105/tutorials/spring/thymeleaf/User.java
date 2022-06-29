package jhkim105.tutorials.spring.thymeleaf;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
  private String id;
  private String username;
  private Role role;

}
