package com.example.springcacheredis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DateService {

  @Cacheable("dateString")
  public String getDateString(String pattern) {
    log.info("getDateString called");
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }


  @CacheEvict(value = "dateString", allEntries = true)
  public void evictCache() {

  }

  @CachePut(value = "dateString")
  public String putCache(String pattern) {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
  }

}
