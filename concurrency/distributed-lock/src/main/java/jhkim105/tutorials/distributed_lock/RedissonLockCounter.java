package jhkim105.tutorials.distributed_lock;


import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonLockCounter {

  private final RedissonClient redissonClient;
  private final Counter counter = new Counter();

  /**
   * 잠금에 실패하면 최대 대기시간동안 대기
   */
  public void increaseUsingRedissonLock() {
    String key = "counter_increaseUsingRedissonLock";
    RLock lock = redissonClient.getLock(key);
    try {
      boolean res = lock.tryLock(30000, 1000, TimeUnit.MILLISECONDS);
      if (res) {
        try {
          increase();
        } finally {
          if(lock.isHeldByCurrentThread()) {
            lock.unlock();
          }
        }
      }
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }



  /**
   * 잠금에 실패하면 실행 안함
   */
  public void increaseUsingAtomicLong() {
    String key = "counter_increaseUsingAtomicLong";
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
    String key = "counter_increaseSynchronizedUsingRedissonLock";
    RLock lock = redissonClient.getLock(key);
    lock.lock();
    increase();
    lock.unlock();
  }

  private void increase() {
    counter.increase();
  }
}
