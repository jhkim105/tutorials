package jhkim105.tutorials.spring.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AppPropertiesTest {

  @Autowired
  AppProperties appProperties;

  @Test
  void test() {
    log.info("", appProperties.getStoragePath());
    assertThat(appProperties.getStoragePath()).isNotBlank();
  }
}