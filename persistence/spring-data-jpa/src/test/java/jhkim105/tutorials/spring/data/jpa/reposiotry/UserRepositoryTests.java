package jhkim105.tutorials.spring.data.jpa.reposiotry;

import jhkim105.tutorials.spring.data.jpa.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@Transactional
public class UserRepositoryTests {

  @Test
  @Sql(scripts = "/user.sql")
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
  }
}
