package jhkim105.tutorials.spring.mqtt.concurrency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;
import jhkim105.tutorials.spring.mqtt.concurrency.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


@Slf4j
@SpringBootTest
@Import(RedisConfig.class)
class RedissonLockTest {

  @Autowired
  RedissonClient client;

  @Test
  void testAtomicLong() throws InterruptedException {
    RAtomicLong atomicLong = client.getAtomicLong("key-1");
    atomicLong.compareAndSet(0, 1);
    atomicLong.expire(1, TimeUnit.SECONDS);
    assertThat(atomicLong.compareAndSet(0, 1)).isEqualTo(false);
    Thread.sleep(1000l);
    assertThat(atomicLong.compareAndSet(0, 1)).isEqualTo(true);
  }

}
