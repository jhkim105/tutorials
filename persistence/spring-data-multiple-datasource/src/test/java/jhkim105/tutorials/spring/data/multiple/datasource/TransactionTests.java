package jhkim105.tutorials.spring.data.multiple.datasource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jhkim105.tutorials.spring.data.multiple.datasource.product.Product;
import jhkim105.tutorials.spring.data.multiple.datasource.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class TransactionTests {

  @Autowired
  ProductRepository productRepository;

  String productId;

  @BeforeEach
  void setUp() {
    Product product = Product.builder().name("Product 01").build();
    productId = productRepository.save(product).getId();
  }

  @Test
  void test() {
    assertNotNull(productRepository.getById(productId));
  }

}
