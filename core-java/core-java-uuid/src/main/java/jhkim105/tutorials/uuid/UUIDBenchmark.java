package jhkim105.tutorials.uuid;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class UUIDBenchmark {


  public static void main(String[] args) throws InterruptedException {
    var threadCount = 128;
    var iterationCount = 100_000;
    var uuidMap = new ConcurrentHashMap<UUID, Long>();
    AtomicLong collisionCount = new AtomicLong();
    long startNanos = System.nanoTime();
    CountDownLatch endLatch = new CountDownLatch(threadCount);

    for (long i = 0; i < threadCount; i++) {
      long threadId = i;
      new Thread(() -> {
        for (long j = 0; j < iterationCount; j++) {
          UUID uuid = UUID.randomUUID();
          Long existingUUUID = uuidMap.put(uuid, (threadId * iterationCount) + j);
          if (existingUUUID != null) {
            collisionCount.incrementAndGet();
          }
        }
        endLatch.countDown();
      }).start();
    }
    endLatch.await();
    System.out.println(String.format("[UUID.randomUUID()] %s UUIDs generated,  %s collisions in %d ms",
        threadCount * iterationCount, collisionCount, TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNanos)));
  }
}
