package com.example.demo.user;

import com.example.demo.JasyptConfig;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
@Import(JasyptConfig.class)
public class UserRepositoryTest {

  @Autowired
  UserRepository repository;

  @Autowired
  PBEStringEncryptor stringEncryptor;

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

  @Test
  @Sql(scripts = "/testdata-user.sql")
  void get() {
    List<User> list = repository.findAll();
    log.debug("{}", list);
    Assertions.assertThat(list.get(0).getUsername()).isEqualTo("tuser01");
    Assertions.assertThat(list.get(1).getUsername()).isEqualTo("tuser02");
  }


  @Test
  @Disabled
  void encrypt() {
    String str1 = stringEncryptor.encrypt("tuser01"); //hdj/V2JOebd9Wjtih626pg==
    String str2 = stringEncryptor.encrypt("tuser02"); //q1vbW9CYmhUeez0Wy4+PkA==
    log.debug("{}, {}", str1, str2);
  }

}
