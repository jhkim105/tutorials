commons-configuration2를 사용하여 Database properties, Runtime properties 구현
=================================

commons-configuration 을 commons-configuration2 로 업그레이드 하기

### dependency
```xml
    <dependency>
      <groupId>org.apache.commons</groupId>
      <artifactId>commons-configuration2</artifactId>
      <version>2.7</version>
    </dependency>

    <dependency>
      <groupId>commons-beanutils</groupId>
      <artifactId>commons-beanutils</artifactId>
      <version>1.9.4</version>
    </dependency>
```
## Database properties

```java
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
```

## Runtime properties
builder를 bean 으로 등록하고, builder.getConfiguration()을 호출해야 갱신된 프로퍼티를 읽을 수 있다. (이전 버전과 다른 부분)
```java
builder.getConfiguration().getString("key")
```
주기적으로 리로딩
```java
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
```

요청시 리로딩
```java
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
```





## References
https://commons.apache.org/proper/commons-configuration/apidocs/org/apache/commons/configuration2/DatabaseConfiguration.html

https://commons.apache.org/proper/commons-configuration/userguide/upgradeto2_0.html





