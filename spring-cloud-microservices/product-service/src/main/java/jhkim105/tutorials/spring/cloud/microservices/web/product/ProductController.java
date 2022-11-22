package jhkim105.tutorials.spring.cloud.microservices.web.product;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {


  @GetMapping
  public List<Product> getAll() {
    List<Product> list = new ArrayList<>();
    list.add(Product.builder().id("id01").name("Name 01").build());
    list.add(Product.builder().id("id02").name("Name 02").build());
    list.add(Product.builder().id("id03").name("Name 03").build());
    list.add(Product.builder().id("id04").name("Name 04").build());
    return list;
  }

  @GetMapping("/{id}")
  public Product get(@PathVariable String id) {
    return Product.builder().id(id).name("Name of " + id ).build();
  }
}
