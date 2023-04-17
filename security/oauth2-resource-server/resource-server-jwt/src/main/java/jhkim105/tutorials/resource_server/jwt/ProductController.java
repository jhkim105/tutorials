package jhkim105.tutorials.resource_server.jwt;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Slf4j
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

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable String id) {
    log.info("{} deleted", id);
  }


}
