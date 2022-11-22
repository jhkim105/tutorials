package jhkim105.tutorials.spring.cloud.micrservices.order;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final ProductService productService;

  @GetMapping
  public List<Order> getAll() {
    List<Order> list = new ArrayList<>();
    list.add(Order.builder().id("id01").name("Name 01").productId("id01").build());
    list.add(Order.builder().id("id02").name("Name 02").productId("id02").build());
    list.add(Order.builder().id("id03").name("Name 03").productId("id03").build());
    list.add(Order.builder().id("id04").name("Name 04").productId("id04").build());
    return list;
  }

  @GetMapping("/{id}")
  public Order get(@PathVariable String id) {

    return Order.builder()
        .id(id)
        .build();
  }


}
