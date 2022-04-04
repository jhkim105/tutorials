package jhkim105.tutorials.spring.data.initialize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(excludeFilters = @ComponentScan.Filter(FirstRun.class))
class SpringDataInitializeApplicationTests {

  @Test
  void contextLoads() {
  }

}
