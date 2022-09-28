package jhkim105.tutorials.osiv.repository;

import static org.junit.jupiter.api.Assertions.*;

import jhkim105.tutorials.osiv.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  void get() {
    userRepository.findById("id01");
  }

  @Test
  void getUsingEntityGraph() {
    User user = userRepository.getWithRolesById("id01");
  }

  @Test
  void getUsingFetchJoin() {
    User user = userRepository.getUsingFetchJoin("id01");
  }

}