package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ConcurrencyUtils {
  @Autowired
  private RedissonClient redissonClient;

  public boolean isExecuted(String key) {
    boolean executed = false;
    log.debug("executeOnce called.");
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    long currentValue = atomicLong.addAndGet(1);
    log.debug("currentValue:{}", currentValue);
    if (currentValue > 1) {
      executed = true;
    }

    if (atomicLong.remainTimeToLive() < 0) {
      atomicLong.expire(3, TimeUnit.SECONDS);
    }
    return executed;
  }
}
