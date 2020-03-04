package utils;

import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RoundRobin<T> implements Iterable<T> {

  private final List<T> list;
  private static Integer index = 0;


  public RoundRobin(final List<T> list) {
    this.list = list;
  }

  @Override
  public Iterator<T> iterator() {
    Iterator<T> iterator = new Iterator<T>() {
      @Override
      public boolean hasNext() {
        return true;
      }

      @Override
      public T next() {
        T ret;
        synchronized (index) {
          if (index >= list.size()) {
            index = 0;
          }
          ret = list.get(index++);
        }
        return ret;
      }
    };
    return iterator;
  }
}
