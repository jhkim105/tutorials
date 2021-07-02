package com.example.demo.user;

import com.example.demo.JasyptConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@Import(JasyptConfig.class)
public class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  @Test
  @Rollback(value = false)
  void save() {
    User newUser = User.builder().username("user01").name("Full Name").build();
    repository.save(newUser);

    User user = repository.findByUsername("user01");
    log.debug("{}", user);
    log.info("aaaa");
    log.info("{}", repository.findAll());
  }

}
