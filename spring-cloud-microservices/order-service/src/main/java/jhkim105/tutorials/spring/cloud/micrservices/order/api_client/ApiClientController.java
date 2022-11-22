package jhkim105.tutorials.spring.cloud.micrservices.order.api_client;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/orders/api-client")
@RequiredArgsConstructor
public class ApiClientController {

  private final WebClient webClient;

  @GetMapping("/products")
  public List<Product> getProducts() {
    return this.webClient
        .get()
        .uri("http://localhost:8081/products")
        .attributes(clientRegistrationId("api-client"))
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Product>>() {})
        .block();
  }

}
