package jhkim105.tutorials.spring.data.jpa.ehcache3;


import java.util.concurrent.TimeUnit;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;

//@Configuration
public class CacheConfig {

  @Bean
  public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
    CachingProvider cachingProvider = new EhcacheCachingProvider();
    CacheManager cacheManager = cachingProvider.getCacheManager();
    MutableConfiguration<String, Object> configuration = new MutableConfiguration<>();

    configuration.setStoreByValue(false)
            .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, 1)));

    cacheManager.createCache("user", configuration);
    return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
  }


}
