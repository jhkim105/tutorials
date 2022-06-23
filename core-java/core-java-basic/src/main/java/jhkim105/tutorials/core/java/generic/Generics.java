package jhkim105.tutorials.core.java.generic;

import java.lang.reflect.ParameterizedType;

public class Generics {

  public static <T> T hello(T t) {
    return t;
  }

  public static <T> Class<T> getGenericTypeClass() {
    try {
      String className = ((ParameterizedType) Generics.class.getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
      Class<?> clazz = Class.forName(className);
      return (Class<T>) clazz;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
