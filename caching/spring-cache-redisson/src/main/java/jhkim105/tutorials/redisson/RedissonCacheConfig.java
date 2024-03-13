package jhkim105.tutorials.redisson;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
@EnableConfigurationProperties({CacheProperties.class, RedisProperties.class})
public class RedissonCacheConfig {

  @Bean
  public CacheManager cacheManager(RedissonConnectionFactory redissonConnectionFactory, CacheProperties cacheProperties) {
    String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
    Set<String> cacheNames = cacheNames();
    return RedisCacheManager.builder(redissonConnectionFactory)
        .initialCacheNames(cacheNames)
        .cacheDefaults(redisCacheConfiguration(cacheProperties.getRedis().getTimeToLive(), keyPrefix))
        .withInitialCacheConfigurations(redisCacheConfigurationMap(keyPrefix))
        .build();
  }

  private Set<String> cacheNames() {
    Set<String> cacheNames = new HashSet<>();
    for(String cacheName : Caches.configMap().keySet()) {
      cacheNames.add(cacheName);
    }
    return cacheNames;
  }

  private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap(String keyPrefix) {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    for(String cacheName : Caches.configMap().keySet()) {
      configurationMap.put(cacheName, redisCacheConfiguration(Caches.configMap().get(cacheName), keyPrefix));
    }
    return configurationMap;
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration ttl, String prefix) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
        .prefixCacheNameWith(prefix)
        .entryTtl(ttl)
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
  }

}
