package jhkim105.tutorials.spring.data.multiple.datasource.user;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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

@Configuration
@EnableJpaRepositories(basePackages = "jhkim105.tutorials.spring.data.multiple.datasource.user", entityManagerFactoryRef = "userEntityManagerFactory", transactionManagerRef = "userTransactionManager")
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
        .packages("jhkim105.tutorials.spring.data.multiple.datasource.user")
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
