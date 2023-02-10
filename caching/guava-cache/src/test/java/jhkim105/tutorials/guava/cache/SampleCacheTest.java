package jhkim105.tutorials.guava.cache;

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
    log.info(sampleCache.get("dd.ss.SSS"));
    log.info(sampleCache.get("dd.ss.SSS"));
  }

  @Test
  void delete() {
    log.info(sampleCache.get("dd.ss.SSS"));
    sampleCache.delete("dd.ss.SSS");
    log.info(sampleCache.get("dd.ss.SSS"));
  }

}