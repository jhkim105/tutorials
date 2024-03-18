package jhkim105.tutorials;


import jhkim105.tutorials.domain.User;
import jhkim105.tutorials.domain.UserRepository;
import jhkim105.tutorials.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Slf4j
//@Sql(scripts = "/sql/group-insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
//@Sql(scripts = "/sql/group-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_CLASS)
class UserTest {
  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;
  @Test
  void create() {
    log.debug("> user create start");
    User user  = new User("user01");
    userRepository.save(user);
    log.debug("> user create end");

  }

}
