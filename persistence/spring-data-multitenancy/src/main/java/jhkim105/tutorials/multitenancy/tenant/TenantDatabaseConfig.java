package jhkim105.tutorials.multitenancy.tenant;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = {"jhkim105.tutorials.multitenancy.repository"},
    entityManagerFactoryRef = "tenantEntityManagerFactory",
    transactionManagerRef = "tenantTransactionManager")
@Slf4j
public class TenantDatabaseConfig {
  public static final String PERSISTENCE_UNIT_NAME = "tenant";
  public static final String DOMAIN_PACKAGE = "jhkim105.tutorials.multitenancy.domain";
  @Bean
  @DependsOn("entityManagerFactory")
  public MultiTenantConnectionProvider multiTenantConnectionProvider() {
    log.info("multiTenantConnectionProvider create.");
    return new DataSourceBasedMultiTenantConnectionProviderImpl();
  }

  @Bean
  public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
    return new CurrentTenantIdentifierResolverImpl();
  }

  @Bean
  @ConfigurationProperties(prefix = "multitenancy.tenant.flyway")
  public TenantFlywayProperties tenantFlywayProperties() {
    return new TenantFlywayProperties();
  }

  @Bean(initMethod = "migrate")
  public TenantDatabaseMigrator tenantDatabaseMigrator(TenantRepository tenantRepository, TenantFlywayProperties tenantFlywayProperties) {
    return new TenantDatabaseMigrator(tenantRepository, tenantFlywayProperties);
  }

  @Bean
  @DependsOn("tenantDatabaseMigrator")
  public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
      @Qualifier("multiTenantConnectionProvider") MultiTenantConnectionProvider connectionProvider,
      @Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantIdentifierResolver,
      @Lazy JpaProperties jpaProperties) {
    log.info("tenantEntityManagerFactory create.");
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setPackagesToScan(DOMAIN_PACKAGE);
    entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    Map<String, Object> properties = new HashMap<>();
    properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
    properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
    properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    properties.putAll(jpaProperties.getProperties());
    entityManagerFactoryBean.setJpaPropertyMap(properties);
    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public JPAQueryFactory tenantQueryFactory(@Qualifier("tenantEntityManagerFactory") EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }
}
