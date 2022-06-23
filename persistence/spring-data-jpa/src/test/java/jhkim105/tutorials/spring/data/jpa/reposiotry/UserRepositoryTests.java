package jhkim105.tutorials.spring.data.jpa.reposiotry;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
@Transactional
public class UserRepositoryTests {

  @Test
  @Sql(scripts = "/user.sql")
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
  }
}
