package jhkim105.tutorials.multitenancy.master;


import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "jhkim105.tutorials.multitenancy.master.repository")
@RequiredArgsConstructor
public class MasterDatabaseConfig {

  public static final String PERSISTENCE_UNIT_NAME = "master";
  private static final String DOMAIN_PACKAGE = "jhkim105.tutorials.multitenancy.master.domain";
  private final DataSource dataSource;
  private final EntityManagerFactoryBuilder builder;
  private final ObjectProvider<HibernatePropertiesCustomizer> customizers;

  @Bean
  @Primary
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(ConfigurableListableBeanFactory beanFactory) {
    return builder.dataSource(dataSource)
        .packages(DOMAIN_PACKAGE)
        .persistenceUnit(PERSISTENCE_UNIT_NAME)
        .properties(getAdditionalProperties(beanFactory))
        .build();
  }

  private Map<String, ?> getAdditionalProperties(ConfigurableListableBeanFactory beanFactory) {
    Map<String, Object> map = new HashMap<>();
    customizers.orderedStream().forEach(customizer -> customizer.customize(map));
    map.put(AvailableSettings.BEAN_CONTAINER, new SpringBeanContainer(beanFactory));
    return map;
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


}
