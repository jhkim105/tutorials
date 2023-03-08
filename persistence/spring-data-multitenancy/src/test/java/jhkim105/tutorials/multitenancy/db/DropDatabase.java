package jhkim105.tutorials.multitenancy.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, properties = "spring.jpa.hibernate.ddl-auto=none")
@Slf4j
class DropDatabase {
  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeAll
  static void beforeAll() {
    if (System.getProperty("db.name") == null) {
      throw new IllegalStateException("System property 'db.name' must be set");
    };
  }

  @Test
  void drop() {
    String dbName = System.getProperty("db.name");
    log.info("Drop database [{}]", dbName);
    jdbcTemplate.execute("drop database " + dbName);

  }
}
