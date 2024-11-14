package jhkim105.tutorials.domain;


import java.util.List;
import jhkim105.tutorials.domain.product.Product;
import jhkim105.tutorials.domain.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BatchInsertTest {

  @Autowired
  ProductRepository productRepository;


  @Test
  void test() {
    var products = List.of(new Product("p01", 1000l), new Product("p02", 2000l));
    productRepository.saveAll(products);
  }



}
