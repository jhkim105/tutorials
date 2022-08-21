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
class DistributedLockApplicationTests {

  @Autowired
  CounterService counterService;

  @Test
  void contextLoads() {
  }

  @BeforeEach
  void setUp() {
    Counter.count = 0;
  }

  @Test
  void increase() {
    int execCount = 1000;
    IntStream.range(0, execCount).parallel().forEach(n -> counterService.increase());
    Assertions.assertThat(Counter.count).isNotEqualTo(execCount);
  }

  @Test
  void increaseUsingShedLock() {
    int execCount = 1000;
    IntStream.range(0, execCount).parallel().forEach(n -> counterService.increaseUsingShedLock());
    Assertions.assertThat(Counter.count).isNotEqualTo(execCount); // 잠금에 실패하면 실행안함.
    log.info("count: {}", Counter.count);
  }

  @Test
  void increaseUsingRedissonLock() {
    int execCount = 100;
    IntStream.range(0, execCount).parallel().forEach(n -> counterService.increaseUsingRedissonLock());
    Assertions.assertThat(Counter.count).isNotEqualTo(execCount); // 잠금에 실패하면 실행안함.
    log.info("count: {}", Counter.count);
  }

  @Test
  void increaseSynchronizedUsingRedissonLock() {
    int execCount = 1000;
    IntStream.range(0, execCount).parallel().forEach(n -> counterService.increaseSynchronizedUsingRedissonLock());
    Assertions.assertThat(Counter.count).isEqualTo(execCount); // 순서대로 모두 실행
    log.info("count: {}", Counter.count);
  }

}
