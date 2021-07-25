package com.example.demo;

import javax.sql.DataSource;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePropertiesConfig {

  @Bean("databaseConfiguration")
  @ConditionalOnProperty(name = "custom.database-properties-cache.enabled", havingValue = "false")
  public DatabaseConfiguration databaseConfiguration(DataSource dataSource) {
    return new DatabaseConfiguration(dataSource, "zt_config", "id", "value");
  }

  @Bean("databaseConfiguration")
  @ConditionalOnProperty(name = "custom.database-properties-cache.enabled", havingValue = "true")
  public CachedDatabaseConfiguration cachedConfiguration(DataSource dataSource) {
    return new CachedDatabaseConfiguration(dataSource, "zt_config", "id", "value");
  }
}
