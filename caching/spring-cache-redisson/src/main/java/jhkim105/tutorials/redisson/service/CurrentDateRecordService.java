package jhkim105.tutorials.redisson.service;



import static jhkim105.tutorials.redisson.Caches.CURRENT_DATE_RECORD;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class CurrentDateRecordService {

  @Cacheable(CURRENT_DATE_RECORD)
  public CurrentDateRecord getCurrentDateRecord(String pattern) {
    log.info("getCurrentDateRecord called");
    return CurrentDateRecord.create(pattern);
  }


  @CacheEvict(value = CURRENT_DATE_RECORD, allEntries = true)
  public void evictCache() {

  }

  @CachePut(value = CURRENT_DATE_RECORD)
  public CurrentDate putCache(String pattern) {
    return CurrentDate.create(pattern);
  }

}
