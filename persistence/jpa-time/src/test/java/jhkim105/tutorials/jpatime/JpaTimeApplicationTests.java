package jhkim105.tutorials.jpatime;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JpaTimeApplicationTests {

  @Autowired
  UserRepository userRepository;

  @Test
  void contextLoads() {
  }

//  @AfterEach
  void clearData() {
    userRepository.deleteAll();
  }

  @Test
  void createUser() {
    User user = new User();
    userRepository.save(user);
    log.info("{}", user);
  }



}
