package jhkim105.tutorials.distributed_lock;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.LockConfiguration;
import net.javacrumbs.shedlock.core.LockingTaskExecutor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CounterService {

  public void increase() {
    Counter.count++;
  }


  private final LockingTaskExecutor lockingTaskExecutor;

  public void increaseUsingShedLock() {
    lockingTaskExecutor.executeWithLock((Runnable) this::increase, lockConfiguration());
  }

  private LockConfiguration lockConfiguration() {
    String lockKey = "counter";
    Instant now = Instant.now();
    Duration lockAtMostFor = Duration.ofMillis(30);
    Duration lockAtLeastFor = Duration.ofMillis(10);
    return new LockConfiguration(now, lockKey, lockAtMostFor, lockAtLeastFor);
  }

  private final RedissonClient redissonClient;

  /**
   * 동시에 한 쓰레드만 실행되고, 나머지는 실행안됨
   */
  public void increaseUsingRedissonLock() {
    String key = "counter";
    RLock lock = redissonClient.getLock(key);
    try {
      boolean res = lock.tryLock(100, 10, TimeUnit.MILLISECONDS);
      if (res) {
        try {
          increase();
        } finally {
          lock.unlock();
        }
      }
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * 동시에 한 쓰레드만 실행되고, 나머지는 실행안됨
   */
  public void increaseUsingAtomicLong() {
    String key = "counter";
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    if (atomicLong.compareAndSet(0, 1)) {
      increase();
      atomicLong.expire(Duration.ofSeconds(5));
    }
  }

  /**
   * 모든 요청이 실행됨
   */
  public void increaseSynchronizedUsingRedissonLock() {
    String key = "counter";
    RLock lock = redissonClient.getLock(key);
    lock.lock();
    increase();
    lock.unlock();
  }
}
