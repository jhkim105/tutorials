package jhkim105.tutorials.multitenancy.db;

import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, properties = "spring.jpa.hibernate.ddl-auto=none")
@Slf4j
class LoadData {

  @Autowired
  private DataSource dataSource;

  @Test
  void run() {
    ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("company02.sql"));
    resourceDatabasePopulator.execute(dataSource);
  }
}
