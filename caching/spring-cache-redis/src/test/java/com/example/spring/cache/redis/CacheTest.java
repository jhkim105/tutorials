package com.example.spring.cache.redis;

import static com.example.spring.cache.redis.config.CacheConfig.CURRENT_DATE;

import com.example.spring.cache.redis.service.CurrentDateRecordService;
import com.example.spring.cache.redis.service.CurrentDateService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
public class CacheTest {

  @Autowired
  CurrentDateService currentDateService;

  @Autowired
  CurrentDateRecordService currentDateRecordService;

  @Autowired
  RedisTemplate redisTemplate;

  @Autowired
  RedisCacheManager redisCacheManager;

  @Test
  void test() {
    String pattern = "yyyy-MM-dd hh:MM:SSS";
    var currentDate = currentDateService.getCurrentDate(pattern);
    log.info("{}", currentDate);
    log.info("{}", currentDateService.getCurrentDate(pattern));
    log.info("{}", currentDateService.getCurrentDate(pattern));
    redisCacheManager.getCache(CURRENT_DATE).clear();
    log.info("{}", currentDateService.getCurrentDate(pattern));
    log.info("{}", currentDateService.getCurrentDate(pattern));
    log.info("{}", currentDateService.putCache(pattern));
    log.info("{}", currentDateService.getCurrentDate(pattern));
    log.info("{}", currentDateService.getCurrentDate(pattern));
  }

  @Test
  void testRecordClass() {
    String pattern = "yyyy-MM-dd hh:MM:SSS";
    var currentDate = currentDateRecordService.getCurrentDateRecord(pattern);
    log.info("{}", currentDate);
  }
}
