package jhkim105.tutorials.spring.jasypt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class AppPropertiesTest {

  @Autowired
  AppProperties appProperties;


  @Test
  void test() {
    log.info("{}", appProperties);
  }
}