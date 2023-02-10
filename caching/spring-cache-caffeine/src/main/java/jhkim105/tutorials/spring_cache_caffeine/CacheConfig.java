package jhkim105.tutorials.spring_cache_caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@Slf4j
public class CacheConfig {
  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager manager = new SimpleCacheManager();

    ArrayList<CaffeineCache> caches = new ArrayList<>();
    for(CacheConf cacheConf : CacheConf.values()) {
      caches.add(new CaffeineCache(cacheConf.cacheName,
          Caffeine.newBuilder()
              .recordStats()
              .expireAfterAccess(cacheConf.ttl)
              .expireAfterWrite(cacheConf.ttl)
              .maximumSize(cacheConf.maxSize)
              // 만료된 키에 접근할때 실행된다.
              .removalListener((key, value, cause) -> log.info("Removed. key: {}, value: {}, cause: {} ", key, value, cause))
              .build())
      );
    }

    manager.setCaches(caches);
    return manager;
  }


  public static class CacheNames {
    public static final String DATE_STRING = "dateString";
  }

  private enum CacheConf {
    DATE_STRING(CacheNames.DATE_STRING, Duration.ofSeconds(5), 1000);

    private final String cacheName;
    private final Duration ttl;
    private final long maxSize;
    CacheConf(String cacheName, Duration ttl, long maxSize) {
      this.cacheName = cacheName;
      this.ttl = ttl;
      this.maxSize = maxSize;
    }
  }



}
