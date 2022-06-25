package jhkim105.tutorials.multitenancy.controller;

import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/join")
public class UserJoinController {

  private final UserRepository userRepository;


  @PostMapping
  public ResponseEntity<User> join(User user) {
    user = userRepository.save(user);
    return ResponseEntity.ok(user);
  }


}
