package jhkim105.tutorials.spring.cloud.microservices.web;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserInfoController {

  @GetMapping("/user-info")
  public String userInfo(Model model, Principal principal, @AuthenticationPrincipal OidcUser oidcUser) {
    model.addAttribute("username", principal.getName());
    model.addAttribute("token", oidcUser.getIdToken().getTokenValue());
    return "user-info";
  }


}
