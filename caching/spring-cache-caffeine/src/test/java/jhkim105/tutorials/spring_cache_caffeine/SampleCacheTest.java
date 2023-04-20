package jhkim105.tutorials.spring_cache_caffeine;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class SampleCacheTest {


  @Autowired
  SampleCache sampleCache;


  @Test
  void test() {
    log.info(sampleCache.get("dd.ss.SSS"));
    log.info(sampleCache.get("dd.ss.SSS"));
    assertThat(sampleCache.size()).isEqualTo(1);
    log.info(sampleCache.get("dd.ss"));
    log.info(sampleCache.get("dd.ss"));
    assertThat(sampleCache.size()).isEqualTo(2);
  }

  @Test
  void delete() {
    log.info(sampleCache.get("dd.ss.SSS"));
    sampleCache.delete("dd.ss.SSS");
    log.info(sampleCache.get("dd.ss.SSS"));
  }
}