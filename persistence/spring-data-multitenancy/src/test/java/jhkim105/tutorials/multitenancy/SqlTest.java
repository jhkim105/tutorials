package jhkim105.tutorials.multitenancy;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@ActiveProfiles("test")
@SpringBootTest
@Transactional(transactionManager = "tenantTransactionManager")
@Slf4j
public class SqlTest {

  @Autowired
  UserRepository userRepository;

  private String userId;

  @BeforeEach
  void setUp() {
    User user = User.builder()
        .username("user01")
        .build();

    userId = userRepository.save(user).getId();

  }


  /**
   * @Sql 쿼리 실행 후 rollback 이 안된다.
   */
  @Test
  @Sql(scripts = {"/user.sql"}, config = @SqlConfig(transactionManager = "tenantTransactionManager"))
  @Sql(statements = {"DELETE FROM demo_multitenancy_master.user WHERE id = 'tid01';"},
      config = @SqlConfig(transactionManager = "tenantTransactionManager"),
      executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
    log.info("isActualTransactionActive: {}", TransactionSynchronizationManager.isActualTransactionActive());
    assertThat(userRepository.getById("tid01")).isNotNull();
  }

  @Test
  void findByUsername() {
    assertThat(userRepository.findByUsername("user01")).isNotNull();
  }

  @Test
  void deleteByUsername() {
    userRepository.deleteByUsername("user01");
  }

}
