package jhkim105.tutorials.multitenancy;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 *  @Transactional(transactionManager = "tenantTransactionManager") -  @SqlTest rollback X, repository rollback O
 */
@SpringBootTest
@Transactional(transactionManager = "tenantTransactionManager")
@Slf4j
class SqlTest {

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
   * config = @SqlConfig(transactionManager = "tenantTransactionManager") 로 지정해도 안됨
   * 삭제문 추가
   */
  @Test
  @Sql(scripts = {"/user.sql"}, config = @SqlConfig(transactionManager = "tenantTransactionManager"))
  @Sql(statements = {"DELETE FROM user WHERE id = 'tid01';"},
      config = @SqlConfig(transactionManager = "tenantTransactionManager"),
      executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
    log.info("isActualTransactionActive: {}", TransactionSynchronizationManager.isActualTransactionActive());
    assertThat(userRepository.getById("tid01")).isNotNull();
  }


}
