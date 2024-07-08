package jhkim105.tutorials.spring.security.jwt.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final AuthenticationErrorHandler authenticationErrorHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
    log.debug("TokenAuthenticationEntryPoint::path:{}, message:{}", request.getRequestURI(), authException.getMessage());
    authenticationErrorHandler.handleUnauthorized(response);
  }
}
