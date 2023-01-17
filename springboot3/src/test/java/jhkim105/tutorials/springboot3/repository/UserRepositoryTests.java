package jhkim105.tutorials.springboot3.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jhkim105.tutorials.springboot3.JpaConfig;
import jhkim105.tutorials.springboot3.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
class UserRepositoryTests {

  @Autowired
  UserRepository userRepository;

  @Test
  @Sql(scripts = "/sql/user.sql")
  void findByUsername() {
    User user = userRepository.findByUsername("user01");
    assertThat(user).isNotNull();
  }

  @Test
  @Sql(scripts = "/sql/user.sql")
  void existsByUsername() {
    assertThat(userRepository.existsByUsername("user01")).isTrue();
  }

  @Test
  @Sql(scripts = "/sql/user.sql")
  void getUsers() {
    List<User> users = userRepository.getUsers();
    assertThat(users).hasSize(1);
  }
}
