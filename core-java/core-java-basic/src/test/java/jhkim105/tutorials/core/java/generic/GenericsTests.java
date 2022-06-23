package jhkim105.tutorials.core.java.generic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class GenericsTests {

  void hello() {
    String str = Generics.hello("str");
  }


  @Test
  void inferredClass() {
    Foo<Bar> foo = new Foo<>();
    log.info("{}", foo.getMemberClass());
  }
}