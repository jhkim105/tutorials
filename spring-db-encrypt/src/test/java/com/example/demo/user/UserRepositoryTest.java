package com.example.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

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

}
