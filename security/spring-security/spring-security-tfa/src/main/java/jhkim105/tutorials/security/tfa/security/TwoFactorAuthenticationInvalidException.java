package jhkim105.tutorials.security.tfa.security;

import org.springframework.security.core.AuthenticationException;

public class TwoFactorAuthenticationInvalidException extends AuthenticationException {

  public TwoFactorAuthenticationInvalidException(String msg) {
    super(msg);
  }
}
