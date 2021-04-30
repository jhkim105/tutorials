package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class ListUtilsTest {

  @Test
  void findDuplicates() {
    List<String> list = Arrays.asList("a", "a", "b", "c", "a", "b");
    List<String> dupList = ListUtils.findDuplicates(list);
    log.debug("{}", dupList);
  }

  @Test
  public void removeDuplicates() {
    List<String> list = Arrays.asList("a", "a", "b", "c", "a", "b");
    List<String> distinctList = ListUtils.removeDuplicates(list);
    log.debug("{}", distinctList);
  }


  @Test
  void subList() {
    List<Integer> list = new ArrayList<>();
    IntStream.range(0, 17).forEach(i -> list.add(i));
    int sliceSize = 3;
    List<Integer> sublist;
    int index = 0;
    while(!(sublist = ListUtils.subList(list, index++, sliceSize)).isEmpty()) {
      log.debug("{}", sublist);
    }
    log.debug("index:{}", index);
  }

  @Test
  public void findDuplicateIndexes() {
    List<String> list = Arrays.asList("a", "a", "b", "c", "a", "b");
    Map<String, ArrayList<Integer>> duplicateMap = ListUtils.findDuplicateIndexes(list);
    log.warn("result->{}", duplicateMap);
    assertThat(duplicateMap.values()).hasSize(2);
  }
}