package jhkim105.tutorials.spring.springdoc;

import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

  private String id;
  private String username;

  public User(String username) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
  }
}
