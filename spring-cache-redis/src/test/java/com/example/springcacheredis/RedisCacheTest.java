package com.example.springcacheredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class RedisCacheTest {

  @Autowired
  DateService dateService;

  @Autowired
  RedisTemplate redisTemplate;

  @Autowired
  RedisCacheManager redisCacheManager;

  @Test
  void test() {
    String pattern = "yyyy-MM-dd hh:MM:SSS";
    String dateString = dateService.getDateString(pattern);
    log.info(dateString);
    log.info(dateService.getDateString(pattern));
    log.info(dateService.getDateString(pattern));
    redisCacheManager.getCache("dateString").clear();
    log.info(dateService.getDateString(pattern));
    log.info(dateService.getDateString(pattern));
    log.info(dateService.putCache(pattern));
    log.info(dateService.getDateString(pattern));
    log.info(dateService.getDateString(pattern));
  }
}
