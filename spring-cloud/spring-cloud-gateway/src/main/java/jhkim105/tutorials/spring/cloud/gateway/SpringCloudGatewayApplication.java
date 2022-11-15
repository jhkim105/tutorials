package jhkim105.tutorials.spring.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class SpringCloudGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringCloudGatewayApplication.class, args);
  }


  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
        .route("path_route", r -> r.path("/route/**")
            .uri("http://localhost:7777"))
        .build();
  }

  @GetMapping("/proxy")
  public Mono<ResponseEntity<byte[]>> proxy(ProxyExchange<byte[]> proxy) throws Exception {
    return proxy.uri("http://localhost:7777/one").get();
  }

}
