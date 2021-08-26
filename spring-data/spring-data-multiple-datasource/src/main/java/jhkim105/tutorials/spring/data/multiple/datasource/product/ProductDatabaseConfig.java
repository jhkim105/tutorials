package jhkim105.tutorials.spring.data.multiple.datasource.product;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "jhkim105.tutorials.spring.data.multiple.datasource.product", entityManagerFactoryRef = "productEntityManagerFactory", transactionManagerRef = "productTransactionManager")
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
        .packages("jhkim105.tutorials.spring.data.multiple.datasource.product")
        .persistenceUnit("product")
        .build();
  }

  @Bean
  public PlatformTransactionManager productTransactionManager(
      @Qualifier("productEntityManagerFactory") EntityManagerFactory productEntityManagerFactory) {
    return new JpaTransactionManager(productEntityManagerFactory);
  }

}
