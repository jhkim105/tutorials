package com.example.cache;

import static com.example.cache.CacheConfig.DATE_STRING_CACHE;

import java.util.Arrays;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import utils.DateUtils;
import utils.RoundRobin;

@Service
public class DateService {



  @Cacheable(value = DATE_STRING_CACHE)
  public String getDateString(String format) {
    return DateUtils.getDateString(format);
  }


  @CacheEvict(value = DATE_STRING_CACHE, allEntries = true)
  @Scheduled(fixedRateString = "1000")
  public void evictCache() {

  }


  @Cacheable(value ="roundRobin")
  public RoundRobin<?> getRoundRobin() {
    String now = DateUtils.getDateString("mm:ss:SSS");
    RoundRobin<String> roundRobin = new RoundRobin<>(Arrays.asList(
        String.format("[%s][%s]", now, 1),
        String.format("[%s][%s]", now, 2)
//        String.format("[%s][%s]", now, 3)
        ));
    return roundRobin;
  }

  public String getNotCachedDateString(String s) {
    return getDateString(s);
  }

}
