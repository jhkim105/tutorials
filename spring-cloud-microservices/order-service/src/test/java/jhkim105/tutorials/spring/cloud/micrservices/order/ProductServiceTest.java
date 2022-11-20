package jhkim105.tutorials.spring.cloud.micrservices.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

  @Autowired
  ProductService productService;

  @Test
  void test() {
    productService.get("id01");
  }

}