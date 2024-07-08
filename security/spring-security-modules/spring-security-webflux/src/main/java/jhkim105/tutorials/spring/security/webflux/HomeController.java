package jhkim105.tutorials.spring.security.webflux;


import java.security.Principal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

  @GetMapping("/")
  public Mono<String> home(Mono<Principal> principal) {
    return principal
        .map(Principal::getName)
        .map(name -> String.format("Hello, %s", name));
  }


}
