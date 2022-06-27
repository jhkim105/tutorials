package jhkim105.tutorials.spring.data.multiple.datasource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import jhkim105.tutorials.spring.data.multiple.datasource.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(transactionManager = "productTransactionManager")
class SqlTests {

  @Autowired
  ProductRepository productRepository;


  @Test
  @Sql(scripts = "/product.sql", config = @SqlConfig(transactionManager = "productTransactionManager", dataSource = "productDataSource"))
  void findById() {
    assertNotNull(productRepository.findById("id01").orElse(null));
  }
}
