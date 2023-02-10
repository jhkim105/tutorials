package jhkim105.tutorials.spring_cache_caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SampleCache {

  private LoadingCache<String, String> cache;

  @PostConstruct
  void initCache() {
    cache = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(5, TimeUnit.SECONDS)
        .removalListener((key, value, cause) -> {
          log.info("Removed. {}", new Date());
        })
        .build(key -> new Date().toString());
  }

  public String get(String pattern) {
    return cache.get(pattern);
  }

  public void delete(String pattern) {
    cache.invalidate(pattern);
  }

  @Scheduled(fixedRate = 1_000)
  private void cleanUpCache() {
    cache.cleanUp();
    log.debug("caches.cleanUp() executed.");
  }
}
