package utils;

import java.util.Arrays;
import java.util.List;
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


}