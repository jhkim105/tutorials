package jhkim105.tutorials.spring.multi.module.api;

import jhkim105.tutorials.spring.multi.module.core.ServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {

  private final ServiceProperties serviceProperties;

  @GetMapping("/")
  public ResponseEntity<String> home() {
    return ResponseEntity.ok(serviceProperties.getAppName());
  }


}
