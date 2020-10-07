package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Slf4j
@SpringBootTest
@Import(RedisConfig.class)
public class RedissonLockTest {

  @Autowired
  MyCounter myCounter;

  @Test
  public void test() throws InterruptedException {
    int numberOfThreads = 5;
    ExecutorService service = Executors.newFixedThreadPool(10);
    CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
    for (int i = 0; i < numberOfThreads; i++) {
      service.submit(() -> {
//        IntStream.range(0,10).forEach(n -> myCounter.increment());
//        IntStream.range(0,10).forEach(n -> myCounter.incrementWithWait());
        IntStream.range(0, 10).forEach(n -> myCounter.incrementWithRedissonLock());
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    log.debug("end count:{}", myCounter.count);
  }

  @Test
  public void testSingle() throws InterruptedException {
    IntStream.range(0, 10).forEach(n -> myCounter.incrementWithRedissonLock());
    log.debug("end count:{}", myCounter.count);
  }
}
