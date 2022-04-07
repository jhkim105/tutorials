package jhkim105.tutorials.commons.configuration;

import javax.sql.DataSource;
import org.apache.commons.configuration.DatabaseConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePropertiesConfig {

  @Bean
  @ConditionalOnProperty(name = "custom.database-properties-cache.enabled", havingValue = "true")
  public CachedDatabaseConfiguration cachedConfiguration(DataSource dataSource) {
    return new CachedDatabaseConfiguration(dataSource, "zt_config", "id", "value");
  }

  @Bean
  @ConditionalOnMissingBean(CachedDatabaseConfiguration.class)
  public DatabaseConfiguration databaseConfiguration(DataSource dataSource) {
    return new DatabaseConfiguration(dataSource, "zt_config", "id", "value");
  }


}
