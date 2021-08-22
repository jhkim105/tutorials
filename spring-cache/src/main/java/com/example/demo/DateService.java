package com.example.demo;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import utils.DateUtils;
import utils.RoundRobin;

@Service
public class DateService {



  @Cacheable("dateString")
  public String getDateString(String format) {
    return DateUtils.getDateString(format);
  }


  @CacheEvict(value = "dateString", allEntries = true)
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


  @Autowired
  private DateService self;

  public String getCachedDateString(String s) {
    return self.getDateString(s);
  }
}
