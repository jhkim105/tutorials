package jhkim105.tutorials.domain;


import static org.junit.jupiter.api.Assertions.assertThrows;

import jhkim105.tutorials.JpaConfig;
import jhkim105.tutorials.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({JpaConfig.class, QuickPerfSqlConfig.class})
@Slf4j
class UserTest {

  @Autowired
  private UserRepository userRepository;

  @Test
  @ExpectSelect(1)
  void jpql_not_follow_global_fetch_strategy() {
    var list = userRepository.findAllWithOrdersUsingEntityGraph();
    list.stream().peek(o -> log.debug("{}", o.getOrders().size())).findFirst();
    assertThrows(LazyInitializationException.class, () -> { //  JPQL 은 글로벌 페치전략 을 따르지 않는다
      list.stream().peek(o -> log.debug("{}", o.getCoupons().size())).findFirst();
    });
  }


}
