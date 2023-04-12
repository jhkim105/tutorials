package jhkim105.tutorials.sso.client;


import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

  @GetMapping("/")
  public String home() {
    return "index";
  }

}
