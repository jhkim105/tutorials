package jhkim105.tutorials.keycloak_spring;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class LogoutController {

  @GetMapping("logout")
  public RedirectView logout(HttpServletRequest request) throws ServletException {
    request.logout();
    return new RedirectView("/");
  }
}
