package jhkim105.tutorials.distributed_lock;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class LockTest {

  private final int execCount = 100;

  private Counter counter;

  @Autowired
  private ShedLockCounter shedLockCounter;

  @Autowired
  private RedissonLockCounter redissonLockCounter;

  @Autowired
  private CounterService counterService;

  @Test
  void contextLoads() {
  }

  @BeforeEach
  void setUp() {
    counter = new Counter();
  }

  @Test
  void increase() {
    IntStream.range(0, execCount).parallel().forEach(n -> counter.increase());
    Assertions.assertThat(counter.getCount()).isNotEqualTo(execCount);
  }

  @Test
  void increaseUsingShedLock() {
    IntStream.range(0, execCount).parallel().forEach(n -> shedLockCounter.increaseUsingShedLock());
    Assertions.assertThat(counter.getCount()).isNotEqualTo(execCount); // 잠금에 실패하면 실행안함.
    log.info("count: {}", counter.getCount());
  }

  @Test
  void increaseUsingRedissonLock() {
    IntStream.range(0, execCount).parallel().forEach(n -> redissonLockCounter.increaseUsingRedissonLock());
    Assertions.assertThat(counter.getCount()).isEqualTo(execCount); // 잠금에 실패하면 최대 대기시간동안 대기
    log.info("count: {}", counter.getCount());
  }

  @Test
  void increaseUsingAtomicLong() {
    IntStream.range(0, execCount).parallel().forEach(n -> redissonLockCounter.increaseUsingAtomicLong());
    Assertions.assertThat(counter.getCount()).isNotEqualTo(execCount); // 잠금에 실패하면 실행안함.
    log.info("count: {}", counter.getCount());
  }

  @Test
  void increaseSynchronizedUsingRedissonLock() {
    IntStream.range(0, execCount).parallel().forEach(n -> redissonLockCounter.increaseSynchronizedUsingRedissonLock());
    Assertions.assertThat(counter.getCount()).isEqualTo(execCount); // 순서대로 모두 실행
    log.info("count: {}", counter.getCount());
  }


}
