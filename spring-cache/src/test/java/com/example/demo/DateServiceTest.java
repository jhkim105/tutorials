package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.RoundRobin;

@SpringBootTest
@Slf4j
class DateServiceTest {

  @Autowired
  DateService dateService;

  @Test
  public void test() throws InterruptedException {
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

}