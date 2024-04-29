package jhkim105.tutorials.spring.events;

import jhkim105.tutorials.spring.application.UserService;
import jhkim105.tutorials.spring.domain.User;
import jhkim105.tutorials.spring.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TransactionalEventTest {


  @Autowired
  UserService userService;

  @Autowired
  UserRepository userRepository;

  @AfterEach
  void afterEach() {
    userRepository.deleteAllInBatch();
  }

  @Test
  void save() {
    userService.save(new User("user 01"));
  }


}