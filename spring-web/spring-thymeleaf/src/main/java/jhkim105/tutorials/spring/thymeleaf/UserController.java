package jhkim105.tutorials.spring.thymeleaf;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {


  @GetMapping
  public String view(Model model) {
    Role[] roles = Role.values();
    Arrays.stream(roles).forEach(r -> r.setLabel(r.name() + " Label"));
    model.addAttribute("user", new User());
    model.addAttribute("roles", roles);

    return "user";
  }

  @PostMapping
  public String save(User user, Model model) {
    log.info("user-> {}", user);
    Role[] roles = Role.values();
    Arrays.stream(roles).forEach(r -> r.setLabel(r.name() + " Label"));
    model.addAttribute("user", user);
    model.addAttribute("roles", roles);
    return "user";
  }
}
