package jhkim105.tutorials.domain.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jhkim105.tutorials.config.JpaConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
@Transactional
class UserRepositoryTests {

  @Autowired
  UserRepository userRepository;

  @PersistenceContext
  private EntityManager em;

  @Test
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
  }


  @Test
  void create() {
    User user = new User("username001");
    userRepository.save(user);
  }

  @Test
  void createByEntityManager() {
    User user = new User("username001");
    em.persist(user);
  }

  @Test
  void getUsers() {
    userRepository.getUsers();
  }

  @Test
  void findById() {
    User user = userRepository.findById("id01").get();

  }

}
