package jhkim105.tutorials.spring.session.redis;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

  private String id;
  private String username;
  private LocalDateTime createdDate;

  public User(String username) {
    this.id = UUID.randomUUID().toString();
    this.username = username;
    this.createdDate = LocalDateTime.now();
  }
}
