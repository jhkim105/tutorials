package jhkim105.tutorials.core.java.generic;

import java.lang.reflect.ParameterizedType;

public class Generics {

  public static Class<?> getGenericTypeClass(Class<?> clazz) {
    return (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[0];

  }
}
