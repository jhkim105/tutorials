package utils;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RoundRobin<T> {

  private final List<T> list;

  public RoundRobin(final List<T> list) {
    this.list = list;
  }

  public T next() {
    Collections.shuffle(list);
    T  ret = list.get(0);
    return ret;
  }
}
