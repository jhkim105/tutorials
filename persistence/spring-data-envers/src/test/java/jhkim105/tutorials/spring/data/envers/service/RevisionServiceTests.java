package jhkim105.tutorials.spring.data.envers.service;

import java.util.List;
import jhkim105.tutorials.spring.data.envers.domain.User;
import jhkim105.tutorials.spring.data.envers.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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

  User user;

  @BeforeEach
  void beforeEach() {
    user = userRepository.findById("id01").orElseThrow();
    user.setName("User1");
    userRepository.save(user);
    userRepository.flush();
  }

  @Test
  void getList() {
   List list = revisionService.getList(User.class, 10);
   log.info("{}", list);
  }

  @Test
  void get() {
    long rev = revisionService.getLatestRevisionNumber(User.class, user.getId());
    User user = (User)revisionService.get(User.class, rev);
    log.info("{}", user);
  }


  @Test
  void isModified() {
    long rev = revisionService.getLatestRevisionNumber(User.class, user.getId());
    boolean modified = revisionService.isModified(User.class, user.getId(), "name", rev);
    Assertions.assertThat(modified).isTrue();
  }

}