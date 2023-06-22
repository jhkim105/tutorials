package jhkim105.tutorials.spring.mvc.error;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/view")
public class ViewController {

  @GetMapping
  public String view() {
    throw new RuntimeException("");
//    return "hello";
  }
}
