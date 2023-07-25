package com.example.cache;

import static com.example.cache.CacheConfig.DATE_STRING_CACHE;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utils.DateUtils;

@Service
@RequiredArgsConstructor
public class DateService {

  private final CacheManager cacheManager;


  @Cacheable(value = DATE_STRING_CACHE)
  public String getDateString(String format) {
    return DateUtils.getDateString(format);
  }


  @CacheEvict(value = DATE_STRING_CACHE, allEntries = true)
  @Scheduled(fixedRateString = "1000")
  public void evictAllCache() {

  }

  @CacheEvict(value = DATE_STRING_CACHE, key = "#cacheKey")
  public void evictSingleCache(String cacheKey) {

  }

  public void evictAllCacheByCacheManager() {
    cacheManager.getCache(DATE_STRING_CACHE).clear();
  }

  public void evictSingleCacheByCacheManager(String cacheKey) {
    cacheManager.getCache(DATE_STRING_CACHE).evict(cacheKey);
  }


}
