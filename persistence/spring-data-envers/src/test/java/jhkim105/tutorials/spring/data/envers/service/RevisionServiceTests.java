package jhkim105.tutorials.spring.data.envers.service;

import java.util.List;
import jhkim105.tutorials.spring.data.envers.domain.User;
import jhkim105.tutorials.spring.data.envers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RevisionServiceTests {

  @Autowired
  RevisionService revisionService;


  @Autowired
  UserRepository userRepository;

  @BeforeEach
  void beforeEach() {
    User user = userRepository.findById("id01").orElseThrow();
    user.setName("User1");
    userRepository.save(user);
    userRepository.flush();
  }

  @Test
  void getRevisionObjects() {
   List list = revisionService.getRevisionObjects(User.class, 10);
   log.info("{}", list);
  }


}