package jhkim105.tutorials.spring.data.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
    User newUser = User.builder().username("test01").name("Full Name").phoneNumber("01011112222").build();
    repository.save(newUser);

    User user = repository.findByUsername("test01");
    Assertions.assertThat(user.getUsername()).isEqualTo("test01");
  }

  @Test
  @Disabled
  @Sql(scripts = "/testdata-user.sql")
  void get_평문있을경우() {
    User user = repository.findById("tid01").get();
    Assertions.assertThat(user.getUsername()).isNull();
  }
}
