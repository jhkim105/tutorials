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
## Database properites
https://commons.apache.org/proper/commons-configuration/apidocs/org/apache/commons/configuration2/DatabaseConfiguration.html

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


