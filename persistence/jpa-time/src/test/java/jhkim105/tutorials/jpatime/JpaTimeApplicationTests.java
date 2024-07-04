package jhkim105.tutorials.jpatime;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetTime;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class JpaTimeApplicationTests {

  @Autowired
  UserRepository userRepository;


//  @AfterEach
  void clearData() {
    userRepository.deleteAll();
  }

  @Test
  void test() {
    User user = new User();
    user = userRepository.save(user);
    log.info("{}", user);
    log.info("after->{}", userRepository.findById(user.getId()));

    OffsetTime from = OffsetTime.now().minusHours(1);
    OffsetTime to = OffsetTime.now().plusHours(1);
    var list = userRepository.findByOffsetTimeBetween(from, to);
    log.debug("{}", list);
    assertThat(list).isNotEmpty();
  }



}
