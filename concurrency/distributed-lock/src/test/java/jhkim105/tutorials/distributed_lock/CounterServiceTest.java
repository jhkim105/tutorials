package jhkim105.tutorials.distributed_lock;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class CounterServiceTest {

  @Autowired
  CounterService counterService;

  @Test
  void test() {
    int execCount = 100;

    String counterName = "counter01";
    IntStream.range(0, execCount).parallel().forEach(n -> counterService.increase(counterName));

    Counter counter = counterService.getCounter(counterName);
    Assertions.assertThat(counter.getCount()).isEqualTo(execCount);
    log.info("count: {}", counter.getCount());
  }

}