package com.example.multitenant.config;

import com.example.multitenant.master.CurrentTenantIdentifierResolverImpl;
import com.example.multitenant.master.DataSourceBasedMultiTenantConnectionProviderImpl;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.tool.schema.Action;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.example.multitenant.tenant"},
    entityManagerFactoryRef = "tenantEntityManagerFactory",
    transactionManagerRef = "tenantTransactionManager")
public class TenantDatabaseConfig {
  public static final String PERSISTENCE_UNIT_NAME = "tenant";

  @Bean
  @ConditionalOnBean(name = "masterEntityManagerFactory")
  public MultiTenantConnectionProvider multiTenantConnectionProvider() {
    return new DataSourceBasedMultiTenantConnectionProviderImpl();
  }

  @Bean
  public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
    return new CurrentTenantIdentifierResolverImpl();
  }


  @Bean
  public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
      @Qualifier("multiTenantConnectionProvider") MultiTenantConnectionProvider connectionProvider,
      @Qualifier("currentTenantIdentifierResolver") CurrentTenantIdentifierResolver tenantIdentifierResolver) {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setPackagesToScan("com.example.multitenant.tenant");
    entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    Map<String, Object> properties = new HashMap<>();
    properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
    properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, connectionProvider);
    properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);
    properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
    properties.put(Environment.HBM2DDL_AUTO, Action.UPDATE);
    properties.put(Environment.PHYSICAL_NAMING_STRATEGY, SpringPhysicalNamingStrategy.class);
    entityManagerFactoryBean.setJpaPropertyMap(properties);
    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager tenantTransactionManager(@Qualifier("tenantEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
