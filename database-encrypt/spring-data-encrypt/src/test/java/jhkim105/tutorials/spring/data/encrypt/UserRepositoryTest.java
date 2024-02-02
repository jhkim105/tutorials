package jhkim105.tutorials.spring.data.encrypt;

import java.util.List;
import jhkim105.tutorials.spring.data.encrypt.crypto.CryptoConfig;
import jhkim105.tutorials.spring.data.encrypt.domain.User;
import jhkim105.tutorials.spring.data.encrypt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(CryptoConfig.class)
@Slf4j
class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  @Test
  void save() {
    String username = "test01";
    createUser(username, "name 01", "description 01");

    User user = repository.findByUsername("test01");
    Assertions.assertThat(user.getUsername()).isEqualTo("test01");
  }


  private void createUser(String username, String name, String description) {
    User newUser = User.builder()
        .username(username)
        .name(name)
        .description(description)
        .build();
    repository.save(newUser);
  }

  @Test
  @Disabled
  @Sql(scripts = "/testdata-user.sql")
  void get_평문있을경우() {
    User user = repository.findById("tid01").get();
    Assertions.assertThat(user.getUsername()).isNull();
  }


  @Test
  void searchByName() {
    List<User> users = repository.findByNameStartsWith("Name");
    Assertions.assertThat(users).hasSize(3);
  }

  @Test
  void searchByDescription() {
    List<User> users = repository.findByDescriptionStartsWith("사용자");
    Assertions.assertThat(users).hasSize(0);
  }




}
