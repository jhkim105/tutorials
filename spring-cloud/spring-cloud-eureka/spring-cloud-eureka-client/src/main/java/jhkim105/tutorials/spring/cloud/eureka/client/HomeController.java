package jhkim105.tutorials.spring.cloud.eureka.client;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @Lazy
  @Autowired
  private EurekaClient eurekaClient;

  @Value("${spring.application.name}")
  private String appName;

  @GetMapping("/")
  public String home() {
    return String.format("Hello from '%s'!", eurekaClient.getApplication(appName).getName());
  }


}
