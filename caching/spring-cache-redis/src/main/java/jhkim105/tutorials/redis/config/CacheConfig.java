package jhkim105.tutorials.redis.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class CacheConfig {

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(CacheProperties cacheProperties) {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    String keyPrefix = cacheProperties.getRedis().getKeyPrefix();
    for(String cacheName : Caches.configMap().keySet()) {
      configurationMap.put(cacheName, redisCacheConfiguration(Caches.configMap().get(cacheName), keyPrefix));
    }
    return builder -> builder
        .cacheDefaults(redisCacheConfiguration(cacheProperties.getRedis().getTimeToLive(), keyPrefix))
        .withInitialCacheConfigurations(configurationMap);
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration ttl, String prefix) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
        .prefixCacheNameWith(prefix)
        .entryTtl(ttl)
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
  }


  public class Caches {
    public final static String CURRENT_DATE = "currentDate";
    public final static String CURRENT_DATE_RECORD = "currentDateRecord";

    public static Map<String, Duration> configMap() {
      Map<String, Duration> ttlMap = new HashMap<>();
      ttlMap.put(CURRENT_DATE, Duration.ofSeconds(10));
      return ttlMap;
    }

  }
}
