Multiple DataSource
=========================


### Properties
application.yml
```yml
user:
  datasource:
    url: jdbc:mariadb://localhost/demo_user?createDatabaseIfNotExist=true
    username: root
    password: @db.password@
    dbcp2:
      max-total: 20
      initial-size: 20
      max-idle: 20
      min-idle: 10

product:
  datasource:
    url: jdbc:mariadb://localhost/demo_product?createDatabaseIfNotExist=true
    username: root # not replaced
    password: @db.password@
    dbcp2:
      max-total: 20
      initial-size: 20
      max-idle: 20
      min-idle: 10
```

#### DatabaseConfig
UserDatabaseConfig.java
- DataSource 상세 설정을 위해서 DataSource bean 선언시 @ConfigurationProperties 를 지정해준다.
```java
@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.user", entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "userTransactionManager")
public class UserDatabaseConfig {

  @Bean
  @Primary
  @ConfigurationProperties("user.datasource")
  public DataSourceProperties userDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @ConfigurationProperties("user.datasource.dbcp2")
  public DataSource userDataSource(@Qualifier("userDataSourceProperties") DataSourceProperties userDataSourceProperties) {
    return userDataSourceProperties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
  }

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("userDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages("com.example.demo.user")
        .persistenceUnit("user")
        .build();
  }

  @Bean
  @Primary
  public PlatformTransactionManager userTransactionManager(
      @Qualifier("userEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
```


ProductDatabaaseConfig.java
```java
@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.product", entityManagerFactoryRef = "productEntityManagerFactory", transactionManagerRef = "productTransactionManager")
public class ProductDatabaseConfig {

  @Bean
  @ConfigurationProperties("product.datasource")
  public DataSourceProperties productDataSourceProperties() {
    return new DataSourceProperties();
  }


  @Bean
  @ConfigurationProperties("product.datasource.dbcp2")
  public DataSource productDataSource(@Qualifier("productDataSourceProperties") DataSourceProperties productDataSourceProperties) {
    return productDataSourceProperties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
  }


  @Bean
  public LocalContainerEntityManagerFactoryBean productEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("productDataSource") DataSource productDataSource) {
    return builder
        .dataSource(productDataSource)
        .packages("com.example.demo.product")
        .persistenceUnit("product")
        .build();
  }

  @Bean
  public PlatformTransactionManager productTransactionManager(
      @Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
    return new JpaTransactionManager(productEntityManagerFactory);
  }

}
```


## References
https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-access.configure-two-datasources
