package jhkim105.tutorials.replication.driver.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

  private  final UserRepository userRepository;

  @GetMapping
  public List<User> user() {
    return userRepository.findAll();
  }
}
