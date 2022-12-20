package jhkim105.tutorials.guava.cache;

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
  void test() {
    log.info(dateService.getDateString("dd.ss.SSS"));
    log.info(dateService.getDateString("dd.ss.SSS"));
    log.info(dateService.getDateString("dd.ss.SSS"));
    log.info(dateService.getDateString("dd.ss.SSS"));
  }

  @Test
  void delete() {
    log.info(dateService.getDateString("dd.ss.SSS"));
    dateService.delete("dd.ss.SSS");
    log.info(dateService.getDateString("dd.ss.SSS"));
  }

}