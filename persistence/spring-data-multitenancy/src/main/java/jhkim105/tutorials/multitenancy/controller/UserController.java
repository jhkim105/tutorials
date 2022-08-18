package jhkim105.tutorials.multitenancy.controller;

import java.util.List;
import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import jhkim105.tutorials.multitenancy.service.UserService;
import jhkim105.tutorials.multitenancy.tenant.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserRepository userRepository;

  private final UserService userService;

  @GetMapping
  @TenantContext(key = "#tenantId")
  public ResponseEntity<List<User>> getAll(String tenantId) {
    List<User> users = userRepository.findAll();
    return ResponseEntity.ok(users);
  }

  @PostMapping
  @TenantContext(key = "#user.tenantId")
  public ResponseEntity<User> save(@RequestBody User user) {
    user =  userRepository.save(user);
    return ResponseEntity.ok(user);
  }

  @PostMapping("/{id}/username")
  @TenantContext(key = "#tenantId")
  public ResponseEntity<User> updateUsername(@PathVariable String id, String tenantId, String username) {
    User user =  userService.updateUsername(id, username);
    return ResponseEntity.ok(user);
  }

}
