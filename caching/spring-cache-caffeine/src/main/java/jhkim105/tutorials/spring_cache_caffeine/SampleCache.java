package jhkim105.tutorials.spring_cache_caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class SampleCache implements InitializingBean {

  private LoadingCache<String, String> cache;

  @Override
  public void afterPropertiesSet() throws Exception {
    cache = Caffeine.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(5, TimeUnit.SECONDS)
        .removalListener((key, value, cause) -> {
          log.info("Removed. {}", new Date());
        })
        .build(key -> LocalDateTime.now().format(DateTimeFormatter.ofPattern(key)));
  }

  public String get(String pattern) {
    return cache.get(pattern);
  }

  public long size() {
    return cache.estimatedSize();
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
