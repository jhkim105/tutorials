package jhkim105.tutorials.spring.jsp;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String home() {
    return "home";
  }

  @GetMapping("/el")
  public String el(Model model) {
    model.addAttribute("message", "Hello EL!");
    return "el";
  }

  @GetMapping("/wrong")
  public String wrong() {
    throw new IllegalStateException("Something wrong");
  }
}
