package jhkim105.tutorials.guava.cache;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SampleCache {


  private LoadingCache<String, String> cache;

  @PostConstruct
  void initCache() {
    cache = CacheBuilder.newBuilder()
        .maximumSize(100)
        .expireAfterAccess(5, TimeUnit.SECONDS)
        .removalListener((RemovalListener<String, String>) removal -> {
            log.info("Removed. {}", new Date());
        })
        .build(new CacheLoader<>() {
          public String load(String key) {
            return (new Date()).toString();
          }
        });
  }

  public String get(String pattern) {
    return cache.getUnchecked(pattern);
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
