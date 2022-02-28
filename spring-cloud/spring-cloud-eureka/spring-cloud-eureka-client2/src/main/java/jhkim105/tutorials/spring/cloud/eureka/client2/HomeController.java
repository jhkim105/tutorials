package jhkim105.tutorials.spring.cloud.eureka.client2;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class HomeController {

  private static final String SERVICE_NAME = "spring-cloud-eureka-client";

  @Lazy
  @Autowired
  private EurekaClient eurekaClient;

  @GetMapping("/")
  public String home() {
    InstanceInfo instance = eurekaClient.getApplication(SERVICE_NAME).getInstances().get(0);
    String hostName = instance.getHostName();

    URI uri = UriComponentsBuilder.newInstance()
        .scheme("http")
        .host(hostName)
        .path("")
        .port(instance.getPort())
        .build().toUri();

    ResponseEntity<String> response = new RestTemplate().getForEntity(uri, String.class);
    return response.getBody();
  }


}
