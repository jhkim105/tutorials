package jhkim105.tutorials.oauth2.keycloak;

import java.security.Principal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class UserInfoController {

  @GetMapping("/user-info")
  public String userInfo(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    return "user-info";
  }

}
