package jhkim105.tutorials.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final SecurityErrorHandler securityErrorHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
    log.debug("TokenAuthenticationEntryPoint::path:{}, message:{}", request.getRequestURI(), authException.getMessage());
    securityErrorHandler.handleUnauthorized(response);
  }
}
