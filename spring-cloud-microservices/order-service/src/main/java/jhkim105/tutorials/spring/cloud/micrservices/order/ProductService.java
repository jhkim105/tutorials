package jhkim105.tutorials.spring.cloud.micrservices.order;

import jhkim105.tutorials.spring.cloud.micrservices.order.api_client.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final WebClient productClient;

  public Product get(String id) {
    Mono<Product> response = productClient.get()
        .uri("http://product-service/products/" + id)
        .retrieve().bodyToMono(Product.class);

    return response.block();
  }


}
