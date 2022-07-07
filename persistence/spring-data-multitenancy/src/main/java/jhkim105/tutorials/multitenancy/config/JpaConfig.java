package jhkim105.tutorials.multitenancy.config;


import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "jhkim105.tutorials.multitenancy.master.repository")
@RequiredArgsConstructor
public class JpaConfig {

  public static final String PERSISTENCE_UNIT_NAME = "master";
  private static final String DOMAIN_PACKAGE = "jhkim105.tutorials.multitenancy.master.domain";
  private final DataSource dataSource;
  private final EntityManagerFactoryBuilder builder;

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    return builder.dataSource(dataSource)
        .packages(DOMAIN_PACKAGE)
        .persistenceUnit(PERSISTENCE_UNIT_NAME)
        .build();
  }


  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


}
