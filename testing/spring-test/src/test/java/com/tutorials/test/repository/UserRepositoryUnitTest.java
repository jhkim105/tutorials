package com.tutorials.test.repository;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.tutorials.test.domain.User;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 데이터베이스를 사용
class UserRepositoryUnitTest {
  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository.save(new User("user001", "pass001"));
    userRepository.save(new User("user002", "pass002"));
  }

  @AfterEach
  void destroy() {
    userRepository.deleteAll();
  }

  @Test
  void findAll() {
    List<User> list = userRepository.findAll();
    assertThat(list.size()).isEqualTo(2);
    assertThat(list.get(0).getUsername()).isNotEmpty();
  }

  @Test
  public void testGet_NoSuchElementException() {
    Exception exception = assertThrows(NoSuchElementException.class, () -> {
      userRepository.findById("no_exists_id").get();
    });
    assertThat(exception).isNotNull();
    assertThat(exception.getClass()).isEqualTo(NoSuchElementException.class);
  }

}
