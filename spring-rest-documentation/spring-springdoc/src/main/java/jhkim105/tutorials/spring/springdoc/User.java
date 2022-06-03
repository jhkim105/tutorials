package jhkim105.tutorials.spring.springdoc;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class User {

  private String id;

  public User(String id) {
    this.id = id;
  }
}
