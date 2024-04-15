package jhkim105.tutorials.spring.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AppPropertiesTest {

  @Autowired
  AppProperties appProperties;


  @Test
  void test() {
    System.out.println(appProperties.getRandomValue());
  }


}