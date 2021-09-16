package jhkim105.tutorials.spring.multi.module.api;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@Slf4j
class ApiApplicationTests {

  @Autowired
  ApiProperties apiProperties;

  @Autowired
  Environment env;

  @Test
  void contextLoads() {
    Assertions.assertThat(apiProperties.getAppName()).isEqualTo("api");
  }

  @Test
  void env() {
    Assertions.assertThat(env.getProperty("service.app-name")).isEqualTo("api");
    Assertions.assertThat(env.getProperty("service.app-version")).isEqualTo("1.0");
  }
}


