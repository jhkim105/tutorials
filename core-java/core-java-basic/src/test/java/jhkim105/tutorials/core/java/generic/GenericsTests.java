package jhkim105.tutorials.core.java.generic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GenericsTests {

  @Test
  void getGenericTypeClass() {
    Class<?> clazz = Generics.getGenericTypeClass(Bar.class);
      assertEquals(clazz, String.class);
  }
}