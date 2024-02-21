package jhkim105.tutorials.spring.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.spring.mvc.config.ServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ServicePropertiesTest {

  @Autowired
  ServiceProperties serviceProperties;

  @Test
  void test() {
    log.info("", serviceProperties.getStoragePath());
    assertThat(serviceProperties.getStoragePath()).isNotBlank();
  }
}