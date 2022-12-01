package jhkim105.tutorials.spring.files;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppPropertiesTest {

  @Autowired
  AppProperties appProperties;

  @Test
  void test() {
    Assertions.assertThat(appProperties.getStoragePath()).isNotBlank();
  }
}