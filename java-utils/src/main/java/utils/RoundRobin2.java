package utils;

import java.util.List;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RoundRobin2<T> {

  private final List<T> list;
  private static Integer index = 0; // 한 시스템에서 이 객체는 유일해야 함.


  public RoundRobin2(final List<T> list) {
    this.list = list;
  }

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
}
