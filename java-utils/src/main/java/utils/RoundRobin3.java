package utils;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RoundRobin3<T> {

  private final List<T> list;

  public RoundRobin3(final List<T> list) {
    this.list = list;
  }

  public T next() {
    T ret;
    synchronized (list) {
      ret = list.get(0);
      Collections.rotate(list, -1);
    }
    return ret;
  }
}
