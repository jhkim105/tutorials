package jhkim105.tutorials.spring.multi.module.core;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreApplicationTests {

  @Autowired
  CoreService coreService;

  @Test
  void contextLoads() {
    Assertions.assertThat(coreService.getAppName()).isEqualTo("core");
  }


  @SpringBootApplication
  static class CoreApplication {
  }
}
