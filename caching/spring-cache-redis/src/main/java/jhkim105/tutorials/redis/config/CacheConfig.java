package jhkim105.tutorials.redis.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CacheConfig {

  private final CacheProperties cacheProperties;

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    for(String cacheName : Caches.ttlMap().keySet()) {
      configurationMap.put(cacheName, redisCacheConfiguration(Caches.ttlMap().get(cacheName)));
    }
    return builder -> builder
        .cacheDefaults(redisCacheConfiguration(cacheProperties.getRedis().getTimeToLive()))
        .withInitialCacheConfigurations(configurationMap);
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration ttl) {
    return RedisCacheConfiguration
        .defaultCacheConfig()
        .prefixCacheNameWith(cacheProperties.getRedis().getKeyPrefix())
        .entryTtl(ttl)
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));
  }

}
