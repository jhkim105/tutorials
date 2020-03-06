package utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RoundRobinTest {

  @Test
  public void test() {
    List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f");
    RoundRobin<String> roundRobin = new RoundRobin<>(list);
    int i = 0;
    while(i++ < 10) {
      log.debug(roundRobin.next());
    }
  }

}