package jhkim105.tutorials.config.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequiredArgsConstructor
public class HomeController {

  @Value("${app.name}")
  private String appName;

  @GetMapping("/")
  public String home() {
    return appName;
  }

  private final AppProperties appProperties;

  @GetMapping("/prop")
  public AppProperties prop() {
    return appProperties;
  }

  private final App2Properties app2Properties;
  @GetMapping("/prop2")
  public App2Properties prop2() {
    return app2Properties;
  }
}
