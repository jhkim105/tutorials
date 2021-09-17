package jhkim105.tutorials.spring.data.multiple.datasource.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserRepository userRepository;

  @GetMapping
  public ResponseEntity<Page> findAll(Pageable pageable) {
    Page<User> userPage = userRepository.findAll(pageable);
    return ResponseEntity.ok().body(userPage);
  }
}
