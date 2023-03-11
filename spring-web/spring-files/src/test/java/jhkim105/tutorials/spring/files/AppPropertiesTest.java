package jhkim105.tutorials.spring.files;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
    Assertions.assertThat(appProperties.getStoragePath()).isNotBlank();
    log.debug("{}", appProperties.getResourceMappings());
//    Assertions.assertThat(appProperties.getResourceMappings().values()).hasSize(1);
  }
}