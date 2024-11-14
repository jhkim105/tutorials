package jhkim105.tutorials.core.java.basic;


import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


class CharTests {

  @Test
  void printAsciiCode() {
    IntStream.rangeClosed(0, 255).forEach(i -> System.out.println(String.format("%s: %s", i, (char)i)));
  }

}
