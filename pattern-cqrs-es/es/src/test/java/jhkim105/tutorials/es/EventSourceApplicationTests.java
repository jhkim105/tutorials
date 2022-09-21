package jhkim105.tutorials.es;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.es.domain.User;
import jhkim105.tutorials.es.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EventSourceApplicationTests {

  @Autowired
  UserService userService;


  @Test
  void test() {
    String userId = userService.createUser("test001");
    User user = userService.get(userId);
    assertThat(user.getUsername()).isEqualTo("test001");

    userService.updateUser(userId, "test0001-new");
    user = userService.get(userId);
    assertThat(user.getUsername()).isEqualTo("test0001-new");

    userService.deleteUser(userId);
    user = userService.get(userId);
    assertThat(user).isNull();
  }


}
