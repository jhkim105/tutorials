package jhkim105.tutorials.spring.security.jwt3.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
