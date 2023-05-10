package jhkim105.tutorials.multitenancy.db;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE,
    properties = {"spring.jpa.properties.hibernate.hbm2ddl.auto=update"})
@Slf4j
class CreateSchema {

  @Test
  void run() {
    log.info("DB initialize completed.");
  }
}
