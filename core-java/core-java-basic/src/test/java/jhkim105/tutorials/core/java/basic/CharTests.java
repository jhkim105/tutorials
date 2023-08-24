package jhkim105.tutorials.core.java.basic;


import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class CharTests {

  @Test
  void printAsciiCode() {
    IntStream.rangeClosed(0, 255).forEach(i -> log.debug("{}: {}", i, (char)i));
  }

}
