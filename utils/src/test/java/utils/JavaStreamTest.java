package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JavaStreamTest {

  /**
   * 2차원에서 특정 컬럼만 1차원으로 뽑고 싶다
   */
  @Test
  void listTwoToOneDimension() {
    List<List<String>> source = new ArrayList<>();
    source.add(Arrays.asList("a", "a1"));
    source.add(Arrays.asList("b", "b1"));
    source.add(Arrays.asList("c", "c1"));

    List<String> result = source.stream().map(list -> list.get(1)).collect(Collectors.toList());
    log.debug("{}", result);
  }


  @Test
  void groupBy() {
    List<String> source = Arrays.asList("a", "b", "c", "d", "a", "c");
    Map result = source.stream().collect(Collectors.groupingBy(o -> o));
    log.debug("{}", result);
  }

  @Test
  void distinct() {
    List<String> source = Arrays.asList("a", "b", "c", "d", "a", "c");
    List<String> result = source.stream().distinct().collect(Collectors.toList());
    log.debug("{}", result);
  }
}
