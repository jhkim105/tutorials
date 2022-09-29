package jhkim105.tutorials.spring.mvc;

import jhkim105.tutorials.spring.mvc.config.BeanConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
class BeanTest {

  @Autowired
  BeanConfig.Sample sample;

  @Autowired
  BeanConfig.Sample sample2;

  @Test
  void test() {
    log.info("{}", sample);
    log.info("{}", sample2);
  }
}
