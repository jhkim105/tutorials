package jhkim105.tutorials.spring.cloud.sleuth.webmvc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@EnableAutoConfiguration
@RestController
@CrossOrigin
@Slf4j
public class Frontend {

  String backendBaseUrl = System.getProperty("spring.example.backendBaseUrl", "http://localhost:9000");

  @RequestMapping("/")
  public String callBackend() {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.set("username", "user01");
    HttpEntity<?> request = new HttpEntity<>(headers);
    log.info("Frontend");
    return restTemplate.exchange(backendBaseUrl + "/api", HttpMethod.GET, request, String.class).getBody();
  }

  public static void main(String[] args) {
    SpringApplication.run(Frontend.class,
        "--spring.application.name=frontend",
        "--server.port=8081"
    );
  }
}