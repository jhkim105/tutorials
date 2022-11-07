package jhkim105.tutorials.spring.data.jpa.utils;

import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootTest
@Disabled
class DatabasePopulatorUtilsTests {


  @Autowired
  DataSource dataSource;

  @Test
  void test() {
    Resource sqlResource = new ClassPathResource("sql/test.sql");
    DatabasePopulator databasePopulator = new ResourceDatabasePopulator(sqlResource);
    DatabasePopulatorUtils.execute(databasePopulator, dataSource);
  }

}
