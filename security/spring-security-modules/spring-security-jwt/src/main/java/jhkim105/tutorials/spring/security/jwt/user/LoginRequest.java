package jhkim105.tutorials.spring.security.jwt.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter @Setter
@RequiredArgsConstructor
public class LoginRequest {
  private final String username;

  private final String password;


  public UsernamePasswordAuthenticationToken toUsernamePasswordAuthenticationToken() {
    return new UsernamePasswordAuthenticationToken(this.username, this.password);
  }
}
