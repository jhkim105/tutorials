package com.example.demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Getter
@Slf4j
@Component
public class MyCounter {

  public static int count;

  @Autowired
  private RedissonClient redissonClient;

  public void init() {
    count = 0;
  }

  public void increment() {
    int temp = count;
    log.debug("count1:{}", count);
    count = temp + 1;
    log.debug("count2:{}", count);
  }


  public synchronized void incrementWithWait() {
    increment();
  }

  public void incrementWithRedissonLock() {
    String key = "key1";
    RLock lock = redissonClient.getLock(key);
    try {
      if (lock.tryLock(1, 1, TimeUnit.SECONDS)) {
        increment();
        lock.unlock();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

