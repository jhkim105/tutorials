package jhkim105.tutorials.spring.data.multiple.datasource.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ProductDatabaseConfig.class})
class ProductRepositoryTest {
  @Autowired
  ProductRepository repository;

  String productId;

  @BeforeEach
  void setUp() {
    Product product = Product.builder().name("Product 01").build();
    productId = repository.save(product).getId();
  }

  @Test
  void test() {
    repository.findAll();
  }

  @Test
  void findById() {
    assertNotNull(repository.getById(productId));
  }

  @Test
  @Sql(scripts = "/product.sql", config = @SqlConfig(transactionManager = "productTransactionManager", dataSource = "productDataSource"))
  void findById_Sql() {
    assertNotNull(repository.findById("id01").orElse(null));
  }
}