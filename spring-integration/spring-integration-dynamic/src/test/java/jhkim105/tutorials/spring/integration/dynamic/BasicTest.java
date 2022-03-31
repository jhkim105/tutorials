package jhkim105.tutorials.spring.integration.dynamic;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class BasicTest {

  @Test
  void test() {
    String id = "id01";
    IntStream.range(0, 1000).forEach(i -> log.info("{}", Math.abs(id.hashCode()) % 3));
  }
}
