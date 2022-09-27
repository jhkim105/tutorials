package jhkim105.tutorials.core.java.generic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class GenericsTests {

  @Test
  void getGenericTypeClass() {
    Class<?> clazz = Generics.getGenericTypeClass(Bar.class);
    log.info("{}", clazz);
    assertTrue(clazz.equals(String.class));
  }
}