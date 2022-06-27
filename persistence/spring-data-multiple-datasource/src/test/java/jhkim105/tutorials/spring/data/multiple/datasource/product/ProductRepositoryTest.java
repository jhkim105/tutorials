package jhkim105.tutorials.spring.data.multiple.datasource.product;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(ProductDatabaseConfig.class)
class ProductRepositoryTest {
  @Autowired
  ProductRepository repository;

  @Test
  void test() {
    repository.findAll();
  }

  @Test
  @Sql(scripts = "/product.sql", config = @SqlConfig(transactionManager = "productTransactionManager", dataSource = "productDataSource"))
  void findById() {
    assertNotNull(repository.findById("id01").orElse(null));
  }
}