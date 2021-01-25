package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ListUtils {

  public static <T> List<T> findDuplicates(List<T> list) {
    Map<T, Integer> countMap = new HashMap<>();

    list.forEach(e -> {
      Integer count = countMap.get(e);
      countMap.put(e, count == null ? 1 : count + 1);
    });

    List<T> ret = new ArrayList<>();
    countMap.forEach((k, v) -> {
      if (v > 1) {
        ret.add(k);
      }
    });
    return ret;
  }

  public static <T> List<T> removeDuplicates(List<T> list) {
    LinkedHashSet<T> set = new LinkedHashSet<>(list);
    List<T> resultList = new ArrayList<>(set);
    return resultList;
  }

  public static <T> List<T> subList(List<T> list, int index, int size) {
    int start = size * index;
    int end = Math.min(list.size(), start + size);
    if (start > list.size())
      return Collections.emptyList();
    return list.subList(start, end);
  }

}
