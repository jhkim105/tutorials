package com.example.demo;

import javax.annotation.Resource;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatabasePropertiesTest {

  @Resource(name = "databaseConfiguration")
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
}