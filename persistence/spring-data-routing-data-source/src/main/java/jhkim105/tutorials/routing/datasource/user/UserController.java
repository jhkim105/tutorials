package jhkim105.tutorials.routing.datasource.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
@Slf4j
public class UserController {

  private  final UserRepository userRepository;

  @GetMapping
  public List<User> user() {
    return userRepository.findAll();
  }
}
