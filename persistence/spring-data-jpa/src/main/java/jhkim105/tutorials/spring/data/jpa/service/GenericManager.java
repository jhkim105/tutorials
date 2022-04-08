package jhkim105.tutorials.spring.data.jpa.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public class GenericManager <T, ID extends Serializable>{

  protected JpaRepository<T, ID> repository;
  protected Class<T> entityClass;
  public GenericManager() {
    this.entityClass = getEntityClass();
  }

  @SuppressWarnings("unchecked")
  private Class<T> getEntityClass() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    Type type = genericSuperclass.getActualTypeArguments()[0];
    return (Class<T>) type;
  }

//  private Class<T> getEntityClass() {
//    List<TypeInformation<?>> arguments = ClassTypeInformation.from(this.getClass())
//        .getRequiredSuperTypeInformation(GenericManager.class)
//        .getTypeArguments();
//
//    return (Class<T>) arguments.get(0).getType();
//  }

  public List<T> findAll() {
    return repository.findAll();
  }

  public T get(ID id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("'%s' with id '%s' not found...", this.entityClass.getSimpleName(), id)));
  }

  public T find(ID id) {
    return repository.findById(id).orElse(null);
  }

  public boolean exists(ID id) {
    return repository.existsById(id);
  }

  public T save(T object) {
    return repository.save(object);
  }

  public void removeById(ID id) {
    repository.deleteById(id);
  }

  public void remove(T object) {
    repository.delete(object);
  }
}
