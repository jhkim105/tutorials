package jhkim105.tutorials.jpa.cache.ehcache3;

import org.junit.jupiter.api.Test;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({QuickPerfSqlConfig.class})
@QuickPerfTest
class SpringDataJpaEhcache3ApplicationTests {

  @Autowired
  UserRepository userRepository;

  @Autowired
  OrderRepository orderRepository;

  @Test
  @ExpectSelect(2)
  void testCache() throws InterruptedException {
    userRepository.findById(-99L);
    userRepository.findById(-99L);
    userRepository.findById(-99L);
    Thread.sleep(2000l);
    userRepository.findById(-99L);
  }

  @Test
  @ExpectSelect(4)
  void testNonCache() {
    orderRepository.findById(-99L);
    orderRepository.findById(-99L);
    orderRepository.findById(-99L);
    orderRepository.findById(-99L);
  }

}
