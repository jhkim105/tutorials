package jhkim105.tutorials.spring.data.initialize;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;

@FirstRun
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Disabled
@TestPropertySource(locations = {"classpath:/db-initialize.properties"})
@Slf4j
class DBInitializeTest {

  @Test
  void runFirst() {
    log.info("First Run.");
  }
}
