package jhkim105.tutorials.spring_cache_caffeine;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jhkim105.tutorials.spring_cache_caffeine.CacheConfig.CacheNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DateService {


  @Cacheable(CacheNames.DATE_STRING)
  public String getDateString(String pattern) {
    log.info("getDateString called");
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }


  @CacheEvict(value = CacheNames.DATE_STRING, allEntries = true)
  public void evictCache() {

  }

  @CachePut(value = CacheNames.DATE_STRING)
  public String putCache(String pattern) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }

}
