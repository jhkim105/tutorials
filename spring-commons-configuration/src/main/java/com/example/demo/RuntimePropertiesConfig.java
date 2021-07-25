package com.example.demo;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuntimePropertiesConfig {

  @Value("${custom.runtime-properties-path}")
  private String runtimePropertiesPath;

  @Bean
  public PropertiesConfiguration runtimeProperties() {
    PropertiesConfiguration configuration;
    try {
      configuration = new PropertiesConfiguration(runtimePropertiesPath);
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
    configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
    return configuration;
  }
}
