package jhkim105.tutorials.replication.driver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private  final UserRepository userRepository;

  @GetMapping("/user")
  public ResponseEntity<?> user(int wait) throws InterruptedException {
    wait(wait * 1000);
    return ResponseEntity.ok().build();
  }
}
