package com.example.springcacheredis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
@Slf4j
class DateServiceTest {

  @Autowired
  DateService dateService;

  @Autowired
  RedisTemplate redisTemplate;

  @Test
  void getCache() {
    String dateString = dateService.getDateString("yyyy-MM-dd hh:MM:SSS");
    log.info(dateString);
    log.info(dateService.getDateString("yyyy-MM-dd hh:MM:SSS"));
    log.info(dateService.getDateString("yyyy-MM-dd hh:MM:SSS"));
    log.info(dateService.getDateString("yyyy-MM-dd hh:MM:SSS"));
    log.info(dateService.getDateString("yyyy-MM-dd hh:MM:SSS"));
  }
}