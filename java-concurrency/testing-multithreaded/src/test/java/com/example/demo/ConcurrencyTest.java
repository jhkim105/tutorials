package com.example.demo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@Slf4j
public class ConcurrencyTest {


  @Test
  public void test() throws InterruptedException {
    int numberOfThreads = 5;
    ExecutorService service = Executors.newFixedThreadPool(10);
    MyCounter myCounter = new MyCounter();
    CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
    for(int i = 0; i < numberOfThreads; i++) {
      service.submit(() -> {
//        IntStream.range(0,10).forEach(n -> myCounter.increment());
        IntStream.range(0,10).forEach(n -> myCounter.incrementWithWait());
        countDownLatch.countDown();
      });
    }
    countDownLatch.await();
    log.debug("end count:{}", myCounter.count);
  }


  @Getter
  @Slf4j
  public static class MyCounter {

    public static int count;

    public void increment() {
      int temp = count;
      log.debug("count1:{}", count);
      count = temp + 1;
      log.debug("count2:{}", count);
    }


    public synchronized void incrementWithWait() {
      increment();
    }

  }

}
