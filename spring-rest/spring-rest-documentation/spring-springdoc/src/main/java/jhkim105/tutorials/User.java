package jhkim105.tutorials;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class User {

  private String id;
  private String username;

  @Setter
  private String fileName;

  public User(String username) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
  }
}
