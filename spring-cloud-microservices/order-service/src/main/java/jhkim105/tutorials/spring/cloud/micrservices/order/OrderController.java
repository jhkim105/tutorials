package jhkim105.tutorials.spring.cloud.micrservices.order;


import jhkim105.tutorials.spring.cloud.micrservices.order.api_client.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final ProductService productService;

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {

    return Order.builder()
        .id(id)
        .build();
  }

  @PostMapping
  public Order create(@RequestBody Order order) {

    return null;
  }


  @GetMapping("/products/{id}")
  public Product getProduct(@PathVariable String id) {
    return productService.get(id);
  }

}
