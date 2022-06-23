package jhkim105.tutorials.core.java.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Foo<T extends Serializable> implements Serializable{
  private Class<T> memberClass;

  public Foo() {
    memberClass = getInferredClass();
  }


  @SuppressWarnings("unchecked")
  private Class<T> getInferredClass() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    Type type = genericSuperclass.getActualTypeArguments()[0];
    return (Class<T>) type;
  }

}
