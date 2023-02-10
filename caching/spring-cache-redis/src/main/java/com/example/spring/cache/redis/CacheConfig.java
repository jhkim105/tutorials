package com.example.spring.cache.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableCaching
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
public class CacheConfig {

  public final static String CACHE_DATE_STRING = "dateString";

  @Bean
  public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    for(CacheConf cacheConf : CacheConf.values()) {
      configurationMap.put(cacheConf.cacheName, redisCacheConfiguration(cacheConf.ttl));
    }
    return builder -> builder.withInitialCacheConfigurations(configurationMap);
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration ttl) {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(ttl);
  }


  private enum CacheConf {
    DATE_STRING(CACHE_DATE_STRING, Duration.ofSeconds(5));
    private final String cacheName;
    private final Duration ttl;
    CacheConf(String cacheName, Duration ttl) {
      this.cacheName = cacheName;
      this.ttl = ttl;
    }
  }

}
