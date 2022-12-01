package jhkim105.tutorials.spring.async.webflux;

import java.time.Duration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class WebFluxController {

  @GetMapping("/result_flux")
  public Mono getResult(ServerHttpRequest request) {
    return Mono.defer(() -> Mono.just("Result is ready!"))
        .delaySubscription(Duration.ofMillis(500));
  }

}
