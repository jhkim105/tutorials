package jhkim105.tutorials.security.tfa.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jhkim105.tutorials.security.tfa.security.UserPrincipal;
import jhkim105.tutorials.security.tfa.user.User;
import jhkim105.tutorials.security.tfa.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private final UserRepository userRepository;
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws ServletException, IOException {
    UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
    clearSecurityCode(userPrincipal);
    request.getSession().setAttribute("TFA_USERNAME", null);
    super.onAuthenticationSuccess(request, response, authentication);
  }

  private void clearSecurityCode(UserPrincipal userPrincipal) {
    User user = userRepository.findById(userPrincipal.getId()).get();
    user.setSecurityCode(null);
    userRepository.save(user);
  }


}
