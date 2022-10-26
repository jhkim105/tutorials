package jhkim105.tutorials.spring.security.form_login.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.thymeleaf.util.StringUtils;

;

public class CustomAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

  public CustomAuthenticationEntryPoint(String loginFormUrl) {
    super(loginFormUrl);
  }

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    if (isRestRequest(request)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    } else {
      super.commence(request, response, authException);
    }
  }

  private boolean isRestRequest(HttpServletRequest req) {
    if (StringUtils.equals(req.getHeader("X-Requested-With"), "XMLHttpRequest")
        || StringUtils.startsWith(req.getHeader("Accept"), "application/json")
        || StringUtils.startsWith(req.getHeader("Accept"), "application/xml"))
      return true;

    return false;
  }
}
