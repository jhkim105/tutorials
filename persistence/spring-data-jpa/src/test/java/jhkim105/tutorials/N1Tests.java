package jhkim105.tutorials;

import jhkim105.tutorials.config.JpaConfig;
import jhkim105.tutorials.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@QuickPerfTest
@Import({JpaConfig.class, QuickPerfSqlConfig.class})
class N1Tests {

  @Autowired
  UserRepository userRepository;


  @Test
  @ExpectSelect(3)
  void getUser() {
    userRepository.findById("id01").get();
  }


}
