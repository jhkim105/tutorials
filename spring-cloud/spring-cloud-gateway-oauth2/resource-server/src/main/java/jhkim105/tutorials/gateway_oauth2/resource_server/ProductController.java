package jhkim105.tutorials.gateway_oauth2.resource_server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

  @GetMapping("/{id}")
  public Mono<Product> get(@PathVariable String id) {
    Product product = Product.builder().id(id).name("Name of " + id).build();
    return Mono.just(product);
  }

}
