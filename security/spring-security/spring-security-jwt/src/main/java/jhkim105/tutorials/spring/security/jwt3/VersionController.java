package jhkim105.tutorials.spring.security.jwt3;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriConstants.VERSION)
public class VersionController {


  @GetMapping
  public String get() {
    return "1.0.1";
  }


}
