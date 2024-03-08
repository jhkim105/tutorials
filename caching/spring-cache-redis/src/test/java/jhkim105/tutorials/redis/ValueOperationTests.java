package jhkim105.tutorials.redis;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.annotation.Resource;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class ValueOperationTests {

  @Resource(name = "stringRedisTemplate")
  private ValueOperations<String, String> valueOperations;

  @Test
  void set() throws InterruptedException {
    String key = "key01";
    String value = "value01";
    valueOperations.set(key, value, Duration.ofSeconds(1));
    assertThat(valueOperations.get(key)).isEqualTo(value);
    Thread.sleep(1000l);
    assertThat(valueOperations.get(key)).isNull();
  }

  @Test
  void expire() throws InterruptedException {
    String key = "key01";
    String value = "value01";
    valueOperations.set(key, value, Duration.ofSeconds(10));
    assertThat(valueOperations.get(key)).isEqualTo(value);
    valueOperations.getOperations().expire(key, Duration.ofMillis(1));
    Thread.sleep(10);
    assertThat(valueOperations.get(key)).isNull();
  }

  @Test
  void delete()  {
    String key = "key01";
    String value = "value01";
    valueOperations.set(key, value, Duration.ofSeconds(10));
    assertThat(valueOperations.get(key)).isEqualTo(value);
    valueOperations.getOperations().delete(key);
    assertThat(valueOperations.get(key)).isNull();
  }

}
