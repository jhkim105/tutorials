package com.example.demo;

import javax.sql.DataSource;
import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabasePropertiesConfig {

  @Bean("databaseConfiguration")
  public DatabaseConfiguration databaseConfiguration(DataSource dataSource) throws ConfigurationException {
    BasicConfigurationBuilder<DatabaseConfiguration> builder =
        new BasicConfigurationBuilder<>(DatabaseConfiguration.class);
    builder.configure(
        new Parameters().database()
            .setDataSource(dataSource)
            .setTable("zt_config")
            .setKeyColumn("id")
            .setValueColumn("value")
    );

    return builder.getConfiguration();
  }


}
