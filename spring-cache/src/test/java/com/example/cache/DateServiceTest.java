package com.example.cache;

import com.example.demo.DateService;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DateServiceTest {

  @Autowired
  DateService dateService;

  @Test
  @Disabled
  void roundRobin() throws InterruptedException {
    log.debug("{}", dateService.getRoundRobin().next());
    log.debug("{}", dateService.getRoundRobin().next());
    log.debug("{}", dateService.getRoundRobin().next());
    log.debug("{}", dateService.getRoundRobin().next());
    log.debug("{}", dateService.getRoundRobin().next());
    log.debug("{}", dateService.getRoundRobin().next());
    Runnable r = () -> {
      log.debug("{}", dateService.getRoundRobin().next());
      log.debug("{}", dateService.getRoundRobin().next());
      log.debug("{}", dateService.getRoundRobin().next());
    };

    int i = 0;
    while(i++ < 2) {
      Thread t = new Thread(r);
      t.start();
    }
  }


  @Test
  void externalCall() {
    log.debug(dateService.getDateString("mm:ss SSS"));
    log.debug(dateService.getDateString("mm:ss SSS"));
    log.debug(dateService.getDateString("mm:ss SSS"));
    log.debug(dateService.getDateString("mm:ss:SSS"));
    log.debug(dateService.getDateString("mm:ss:SSS"));
    log.debug(dateService.getDateString("mm:ss:SSS"));
  }

  @Test
  void getNotCachedDateString() {
    IntStream.range(0, 10).forEach(i ->
    {
      log.debug(dateService.getNotCachedDateString("mm:ss SSS"));
      sleep(10);
    });
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getCachedDateString() {
    IntStream.range(0, 10).forEach(i ->
    {
      log.debug(dateService.getCachedDateString("mm:ss SSS"));
      sleep(10);
    });
  }
}