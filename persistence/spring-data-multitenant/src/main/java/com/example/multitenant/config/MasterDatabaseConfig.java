package com.example.multitenant.config;


import javax.persistence.EntityManagerFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.multitenant.master",
    entityManagerFactoryRef = "masterEntityManagerFactory",
    transactionManagerRef = "masterTransactionManager"
)
public class MasterDatabaseConfig {

  @Bean
  @Primary
  @ConfigurationProperties("multitenant.master.datasource")
  public DataSourceProperties masterDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @ConfigurationProperties("multitenant.master.datasource.dbcp2")
  public BasicDataSource masterDataSource() {
    return masterDataSourceProperties().initializeDataSourceBuilder().type(BasicDataSource.class).build();
  }


  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory(EntityManagerFactoryBuilder builder) {
    return builder.dataSource(masterDataSource())
        .packages("com.example.multitenant.master")
        .persistenceUnit("master")
        .build();
  }


  @Bean
  @Primary
  public PlatformTransactionManager masterTransactionManager(@Qualifier("masterEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


}
