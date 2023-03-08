package jhkim105.tutorials.multitenancy.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@TestPropertySource(locations = {"classpath:/db-initialize.properties"})
@Slf4j
class CreateSchema {

  @Test
  void run() {
    log.info("DB initialize completed.");
  }
}
