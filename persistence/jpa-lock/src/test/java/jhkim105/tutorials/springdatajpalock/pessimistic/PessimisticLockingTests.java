package jhkim105.tutorials.springdatajpalock.pessimistic;

import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PessimisticLockingTests {

  @Autowired
  PessimisticService service;

  @Autowired
  PessimisticRepository pessimisticRepository;

  @Test
  void test() {
    int execCount = 100;
    String id = "id01";
    long currentCount = pessimisticRepository.findById(id).get().getCount();

    IntStream.range(0, execCount).parallel().forEach(
        n -> service.increase(id)
    );

    Pessimistic pessimistic = pessimisticRepository.findById(id).get();
    Assertions.assertThat(pessimistic.getCount()).isEqualTo(execCount + currentCount);
  }


}
