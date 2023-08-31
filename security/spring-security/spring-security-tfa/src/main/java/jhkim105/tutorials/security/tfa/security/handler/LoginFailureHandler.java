package jhkim105.tutorials.security.tfa.security.handler;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jhkim105.tutorials.security.tfa.security.TwoFactorAuthenticationInvalidException;
import jhkim105.tutorials.security.tfa.security.TwoFactorAuthenticationRequiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  public LoginFailureHandler() {
    super("/login?error");
  }

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    if (exception instanceof TwoFactorAuthenticationRequiredException) {
      request.getSession().setAttribute("TFA_USERNAME", ((TwoFactorAuthenticationRequiredException)exception).getUsername());
      redirectStrategy.sendRedirect(request, response, "/login_code");
      return;
    }

    if (exception instanceof TwoFactorAuthenticationInvalidException) {
      redirectStrategy.sendRedirect(request, response, "/login_code?error");
      return;
    }

    super.onAuthenticationFailure(request, response, exception);
  }


}
