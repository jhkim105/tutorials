# Spring Cache With Redis


## Dependency
```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
  </dependency>
  <dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.27.1</version>
  </dependency>
```

## Config
- RedissonAutoConfiguration 에서 spring.redis property 사용하여 bean 생성함.
- RedissonCacheConfig.java
```java
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
    for(String cacheName : Caches.ttlMap().keySet()) {
      cacheNames.add(cacheName);
    }
    return cacheNames;
  }

  private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap(String keyPrefix) {
    Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
    for(String cacheName : Caches.ttlMap().keySet()) {
      configurationMap.put(cacheName, redisCacheConfiguration(Caches.ttlMap().get(cacheName), keyPrefix));
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


```

## Refs
- https://github.com/redisson/redisson/blob/master/redisson-spring-boot-starter/README.md

