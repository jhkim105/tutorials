package com.example.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class DateServiceTest {

  @Autowired
  DateService dateService;


  @Test
  void cache() {
    Set<String> data = new HashSet<>();

    String format = "mm:ss SSS";
    data.add(dateService.getDateString(format));
    data.add(dateService.getDateString(format));

    sleep(10);
    dateService.evictSingleCache(format);

    data.add(dateService.getDateString(format));
    data.add(dateService.getDateString(format));
    data.add(dateService.getDateString(format));

    sleep(10);
    dateService.evictSingleCacheByCacheManager(format);

    data.add(dateService.getDateString(format));
    assertThat(data).hasSize(3);
  }

  private void sleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}