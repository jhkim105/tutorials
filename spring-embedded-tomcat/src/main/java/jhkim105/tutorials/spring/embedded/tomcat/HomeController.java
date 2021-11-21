package jhkim105.tutorials.spring.embedded.tomcat;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  public ResponseEntity<String> home() {
    return ResponseEntity.ok("Hello");
  }

  @GetMapping("/ignore")
  public ResponseEntity<String> ignore(HttpServletRequest request) {
    return ResponseEntity.ok("Ignore Access Log");
  }

  @GetMapping("/accessLog")
  public ResponseEntity<String> accessLog(HttpServletRequest request) {
    return ResponseEntity.ok("Access Log");
  }

  @GetMapping("/service_check")
  public ResponseEntity<String> serviceCheck(HttpServletRequest request) {
    return ResponseEntity.ok("Service Check");
  }
}
