package jhkim105.tutorials.security.tfa.security;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class TwoFactorAuthenticationRequiredException extends AuthenticationException {

  @Getter
  private final String username;
  public TwoFactorAuthenticationRequiredException(String username, String msg) {
    super(msg);
    this.username = username;
  }
}
