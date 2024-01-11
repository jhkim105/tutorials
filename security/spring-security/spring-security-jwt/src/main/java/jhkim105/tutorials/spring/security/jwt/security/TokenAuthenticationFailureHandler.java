package jhkim105.tutorials.spring.security.jwt.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  private final AuthenticationErrorHandler authenticationErrorHandler;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {
    log.debug("onAuthenticationFailure");
    authenticationErrorHandler.handleUnauthorized(response);
  }

}
