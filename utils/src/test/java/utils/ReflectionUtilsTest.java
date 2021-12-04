package utils;

import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ReflectionUtilsTest {

  @Test
  void annotatedClass() {
    Set<Class<?>> classes = ReflectionUtils.getAnnotatedClass("utils.annotation", Deprecated.class);
    classes.forEach( c -> System.out.println(c.getName()));
    Assertions.assertThat(classes.iterator().next().getName()).isEqualTo("utils.annotation.AnnotatedClass");
  }

}