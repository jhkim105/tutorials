package com.example.demo;


import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ConfigurationBuilderEvent;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RuntimePropertiesConfig {

  @Value("${custom.runtime-properties-path}")
  private String runtimePropertiesPath;

  @Bean
  public ReloadingFileBasedConfigurationBuilder runtimePropertiesBuilder() {
    ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder =
        new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
            .configure(new Parameters().fileBased()
                .setFileName(runtimePropertiesPath));

    PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(builder.getReloadingController(),
        null, 10, TimeUnit.SECONDS);
    trigger.start();

    return builder;
  }

  @Bean
  public ReloadingFileBasedConfigurationBuilder runtimePropertiesBuilder2() {
    ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder =
        new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
            .configure(new Parameters().fileBased()
                .setFileName(runtimePropertiesPath));

    builder.addEventListener(ConfigurationBuilderEvent.CONFIGURATION_REQUEST,
        event -> {
          log.debug("event:{}", event);
          builder.getReloadingController().checkForReloading(null);});

    return builder;
  }

}
