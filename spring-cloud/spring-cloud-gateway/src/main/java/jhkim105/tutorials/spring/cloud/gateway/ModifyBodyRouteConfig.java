package jhkim105.tutorials.spring.cloud.gateway;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

@Configuration
public class ModifyBodyRouteConfig {

  @Bean
  public RouteLocator requestRoutes(RouteLocatorBuilder builder) {
    String uri = "https://httpbin.org";

    return builder.routes()
        .route("modify_request_body", r -> r.path("/request_filter/modify_body/**")
            .filters(f -> f
                .modifyRequestBody(String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                    (exchange, s) -> Mono.just(new Hello(s.toUpperCase())))
                .setPath("/post"))
            .uri(uri))
        .build();
  }

  @Bean
  public RouteLocator responseRoutes(RouteLocatorBuilder builder) {
    String uri = "https://httpbin.org";

    return builder.routes()
        .route("modify_response_body", r -> r.path("/response_filter/modify_body/**")
            .filters(f -> f
                .modifyResponseBody(String.class, Hello.class, MediaType.APPLICATION_JSON_VALUE,
                    (exchange, s) -> Mono.just(new Hello("New Body")))
                .setPath("/post")).
            uri(uri))
        .build();
  }

  @RequiredArgsConstructor
  @Getter
  @Setter
  static class Hello {
    final String message;

  }

}
