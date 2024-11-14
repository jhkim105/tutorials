package jhkim105.tutorials.redisson.service;




import static jhkim105.tutorials.redisson.config.CacheConfig.Caches.CURRENT_DATE;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CurrentDateService {

  @Cacheable(CURRENT_DATE)
  public CurrentDate getCurrentDate(String pattern) {
    log.info("getCurrentDate called");
    return CurrentDate.create(pattern);
  }

  @CacheEvict(value = CURRENT_DATE, allEntries = true)
  public void evictCache() {

  }

  @CachePut(value = CURRENT_DATE)
  public CurrentDate putCache(String pattern) {
    return CurrentDate.create(pattern);
  }

}
