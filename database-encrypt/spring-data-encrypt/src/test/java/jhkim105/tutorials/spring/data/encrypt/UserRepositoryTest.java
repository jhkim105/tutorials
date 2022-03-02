package jhkim105.tutorials.spring.data.encrypt;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  @Test
  @Rollback(value = false)
  void save() {
    User newUser = User.builder().username("user01").name("Full Name").build();
    repository.save(newUser);

    User user = repository.findByUsername("user01");
    log.info("{}", user);
  }

  @Test
  @Sql(scripts = "/testdata-user.sql")
  void get_평문있을경우() {
    List<User> list = repository.findAll();
    log.debug("{}", list);
    Assertions.assertThat(list.get(0).getUsername()).isNull();
  }
}
