package jhkim105.tutorials.multitenancy;

import javax.sql.DataSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;

class DatabaseTests {


  @Test
  @Disabled
  void dropDatabase() {
    DataSource dataSource = DataSourceBuilder.create()
        .url("jdbc:mariadb://localhost/demo_multitenancy_user1?createDatabaseIfNotExist=true")
        .username("root")
        .password("111111")
        .build();

    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    jdbcTemplate.execute("drop database demo_multitenancy_user1");
  }


}
