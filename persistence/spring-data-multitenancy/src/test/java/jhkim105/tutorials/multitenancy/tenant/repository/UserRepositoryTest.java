package jhkim105.tutorials.multitenancy.tenant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.master.MasterDatabaseConfig;
import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MasterDatabaseConfig.class, TenantDatabaseConfig.class})
@Slf4j
@Transactional(transactionManager = "tenantTransactionManager")
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    User user = User.builder()
        .username("user01")
        .build();

    userRepository.save(user);

  }


  /**
   * @Sql 쿼리 실행 후 rollback 이 안된다.
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


  @Test
  void findByUsername() {
    assertThat(userRepository.findByUsername("user01")).isNotNull();
  }

//  @Test
//  void deleteByUsername() {
//    userRepository.deleteByUsername("user01");
//  }

}
