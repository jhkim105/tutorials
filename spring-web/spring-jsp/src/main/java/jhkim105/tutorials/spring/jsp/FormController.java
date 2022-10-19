package jhkim105.tutorials.spring.jsp;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
@Slf4j
public class FormController {

  @GetMapping
  public String form(Model model) {
    model.addAttribute("command", new Command());
    return "form";
  }

  @PostMapping
  public String save(Command command, Model model) {
    log.info("command-> {}", command);
    model.addAttribute("command", command);
    model.addAttribute("roles", Role.values());
    return "form";
  }


  @Getter
  @Setter
  @ToString
  public static class Command {
    String name;
    Role role;
  }

  public enum Role {
    USER, ADMIN
  }
}
