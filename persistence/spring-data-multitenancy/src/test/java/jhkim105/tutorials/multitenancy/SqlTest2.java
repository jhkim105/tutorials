package jhkim105.tutorials.multitenancy;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
/**
 *
 * @Transactional - @SqlTest rollback O, repository rollback X
 *
 */
class SqlTest2 {

  @Autowired
  UserRepository userRepository;

  private String userId;

  @BeforeEach
  void createUser() {
    User user = User.builder()
        .username("user01")
        .build();

    userId = userRepository.save(user).getId();

  }

  @AfterEach
  void deleteUser() {
    userRepository.deleteById(userId);
  }



  /**
   *
   */
  @Test
//  @Sql(scripts = {"/user.sql"}, config = @SqlConfig(transactionManager = "tenantTransactionManager"))
  // transcationManager 를 지정해도 rollback 이 안된다.
  // @Transactional 로 선언했을 경우에만 rollback 됨
  @Sql(scripts = {"/user.sql"})
  void test() {
    assertThat(userRepository.getById("tid01")).isNotNull();
    assertThat(userRepository.getById(userId)).isNotNull();
  }

}
