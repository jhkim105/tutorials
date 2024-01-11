package jhkim105.tutorials.spring.security.jwt;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(VersionController.URI_VERSION)
public class VersionController {

  public static final String URI_VERSION = "/version";
  @GetMapping
  public String get() {
    return "1.0.1";
  }


}
