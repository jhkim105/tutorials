package jhkim105.tutorials.spring.security.jwt.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@RequiredArgsConstructor
public class TokenAccessDeniedHandler implements AccessDeniedHandler {

  private final SecurityErrorHandler securityErrorHandler;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
    securityErrorHandler.handleAccessDenied(response);
  }
}
