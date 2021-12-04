package utils;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;

public class ReflectionUtils {

  public static Set<Class<?>> getAnnotatedClass(String packageName, Class<? extends Annotation> annotation) {
    Reflections reflections = new Reflections(packageName);
    return reflections.getTypesAnnotatedWith(annotation);
  }
}
