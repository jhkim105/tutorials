package jhkim105.tutorials.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateRequest {
  private String nickname;

  public void applyTo(User currentUser) {
    currentUser.update(nickname);
  }
}
