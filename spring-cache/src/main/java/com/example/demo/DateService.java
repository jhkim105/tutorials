package com.example.demo;

import java.util.Arrays;
import javax.xml.crypto.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
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
  private ApplicationContext applicationContext;

  public String getCachedDateString(String s) {
    DateService service = applicationContext.getBean(DateService.class);
    return service.getDateString(s);
  }
}
