package jhkim105.tutorials.spring.reactive.webclient;

import java.util.List;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
public class WebController {

  @Setter
  private int userServicePort = 8080;

  @GetMapping("/users-blocking")
  public List<User> getUsersBlocking() {
    log.info("Blocking::START");
    String url = getUserServiceUrl();
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null,
        new ParameterizedTypeReference<>() {
        });
    List<User> result = responseEntity.getBody();
    result.forEach(user -> log.info(user.toString()));
    log.info("Blocking::END");
    return result;
  }

  @GetMapping(path = "/users-non-blocking", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<User> getUsersNonBlocking() {
    log.info("NonBlocking::START");
    Flux<User> userFlux = WebClient.create()
        .get()
        .uri(getUserServiceUrl())
        .retrieve()
        .bodyToFlux(User.class);

    userFlux.subscribe(user -> log.info(user.toString()));
    log.info("NonBlocking::END");
    return userFlux;
  }


  private String getUserServiceUrl() {
    return String.format("http://localhost:%d/users", userServicePort);
  }

}
