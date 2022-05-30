package jhkim105.tutorials.spring.swagger2;

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
