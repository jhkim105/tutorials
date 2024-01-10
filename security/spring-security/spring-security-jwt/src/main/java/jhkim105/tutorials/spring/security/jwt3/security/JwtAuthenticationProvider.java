package jhkim105.tutorials.spring.security.jwt3.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
    return new JwtAuthenticationToken(userPrincipal, userPrincipal.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> aClass) {
    return aClass.equals(JwtAuthenticationToken.class);
  }
}
