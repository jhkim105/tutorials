package jhkim105.tutorials.commons.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DatabasePropertiesTest {

  @Autowired
  private DatabaseConfiguration databaseConfiguration;


  @Test
  void getProperty() {
    databaseConfiguration.getProperty("prop1");
    databaseConfiguration.getProperty("prop1");
    databaseConfiguration.getProperty("prop2");
    databaseConfiguration.addProperty("prop1", "1");
    Assertions.assertThat(databaseConfiguration.getProperty("prop1")).isEqualTo("1");
    databaseConfiguration.getProperty("prop2");
    databaseConfiguration.setProperty("prop1", "2");
    Assertions.assertThat(databaseConfiguration.getProperty("prop1")).isEqualTo("2");
    databaseConfiguration.getProperty("prop2");
  }

  @Test
  void getString() {
    databaseConfiguration.getProperty("prop1");
    databaseConfiguration.getProperty("prop1");
    databaseConfiguration.getString("prop1"); // Not applied cache: self-invocation
    databaseConfiguration.getString("prop1"); // Not applied cache: self-invocation
  }

}