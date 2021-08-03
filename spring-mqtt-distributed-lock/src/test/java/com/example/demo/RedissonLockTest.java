package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootTest
@Import(RedisConfig.class)
public class RedissonLockTest {

  @Autowired
  MyCounter myCounter;

  @Test
  public void test_using_parallelStream() {
    int count = 10;
    myCounter.init();
    IntStream.range(0, count).parallel().forEach(n -> myCounter.incrementWithRedissonLock());
    log.debug("end count:{}", myCounter.count);
    assertThat(myCounter.count).isEqualTo(count);
  }

  @Test
  public void test_error_using_parallelStream() {
    int count = 10;
    myCounter.init();
    IntStream.range(0, count).parallel().forEach(n -> myCounter.increment());
    log.debug("end count:{}", myCounter.count);
    assertThat(myCounter.count).isNotEqualTo(count);
  }

  @Test
  public void test_using_executorService() throws InterruptedException {
    int count = 10;
    myCounter.init();
    ExecutorService service = Executors.newFixedThreadPool(10);
    CountDownLatch countDownLatch = new CountDownLatch(count);
    for (int i = 0; i < count; i++) {
      service.submit(() -> {
        myCounter.incrementWithRedissonLock();
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    log.debug("end count:{}", myCounter.count);
    assertThat(myCounter.count).isEqualTo(count);
  }

  @Test
  public void test_using_forkJoinPool() throws InterruptedException {
    int count = 10;
    myCounter.init();
    ForkJoinPool forkJoinPool = new ForkJoinPool(count);
    CountDownLatch countDownLatch = new CountDownLatch(count);
    myCounter.init();
    for (int i = 0; i < count; i++) {
      forkJoinPool.submit(() -> {
        myCounter.incrementWithRedissonLock();
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    log.debug("end count:{}", myCounter.count);
    assertThat(myCounter.count).isEqualTo(count);
  }

}
