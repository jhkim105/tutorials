# Spring Cache With Redis


## Dependency
```xml

    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-cache</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```

## Config
CacheConfig.java
```java
@Configuration
@EnableCaching
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
```

