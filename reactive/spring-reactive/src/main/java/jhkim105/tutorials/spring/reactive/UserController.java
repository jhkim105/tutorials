package jhkim105.tutorials.spring.reactive;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @GetMapping("/users")
  private List<User> getAllUsers() throws Exception {
    Thread.sleep(3000L);

    return Arrays.asList(
       new User("user01", "User 01"),
       new User("user02", "User 02")
    );
  }

}
