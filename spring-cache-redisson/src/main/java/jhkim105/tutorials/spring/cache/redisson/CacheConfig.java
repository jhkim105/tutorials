package jhkim105.tutorials.spring.cache.redisson;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;

@Configuration
@EnableCaching
public class CacheConfig {

  public final static String CACHE_DATE_STRING = "dateString";

  @Bean
  public CacheManager cacheManager(RedissonConnectionFactory redissonConnectionFactory) {
    Set<String> cacheNames = cacheNames();
    Map<String, RedisCacheConfiguration> expiresMap = cacheExpiresMap();
    return RedisCacheManager.builder(redissonConnectionFactory)
        .initialCacheNames(cacheNames).withInitialCacheConfigurations(expiresMap).build();
  }

  private Set<String> cacheNames() {
    Set<String> cacheNames = new HashSet<>();
    cacheNames.add(CACHE_DATE_STRING);
    return cacheNames;
  }

  private Map<String, RedisCacheConfiguration> cacheExpiresMap() {
    Map<String, RedisCacheConfiguration> expiresMap = new HashMap<>();
    expiresMap.put(CACHE_DATE_STRING, redisExpiresConfiguration(600));
    return expiresMap;
  }

  private RedisCacheConfiguration redisExpiresConfiguration(long ttl) {
    return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(ttl));
  }

}
