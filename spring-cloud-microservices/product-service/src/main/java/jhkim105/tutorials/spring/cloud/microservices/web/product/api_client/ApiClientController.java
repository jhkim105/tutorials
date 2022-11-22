package jhkim105.tutorials.spring.cloud.microservices.web.product.api_client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/api-client")
@RequiredArgsConstructor
public class ApiClientController {

  private final OrderClient orderClient;

  @GetMapping("/orders")
  public List<Order> getOrders() {
    return orderClient.getAll();
  }

}
