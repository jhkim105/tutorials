package jhkim105.tutorials.spring_cache_caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
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
public class CaffeineCacheConfig {

  public final static String CACHE_DATE_STRING = "dateString";

  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager manager = new SimpleCacheManager();

    ArrayList<CaffeineCache> caches = new ArrayList<>();
    caches.add(new CaffeineCache(CACHE_DATE_STRING,
        Caffeine.newBuilder()
            .recordStats()
            .expireAfterAccess(10, TimeUnit.SECONDS)
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .maximumSize(1000)
            // 만료된 키에 접근할때 실행된다.
            .removalListener((key, value, cause) -> log.info("Removed. key: {}, value: {}, cause: {} ", key, value, cause))
            .build())
    );
    manager.setCaches(caches);
    return manager;
  }

}
