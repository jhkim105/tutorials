package jhkim105.tutorials.config.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class HomeController {

  @Value("${config.name}")
  private String configName;

  @GetMapping("/")
  public String home() {
    return configName;
  }
}