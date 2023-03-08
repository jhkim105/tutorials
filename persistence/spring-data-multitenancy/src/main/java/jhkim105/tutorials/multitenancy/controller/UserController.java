package jhkim105.tutorials.multitenancy.controller;

import java.util.List;
import jhkim105.tutorials.multitenancy.tenant.context.TenantContext;
import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.service.UserService;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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

  private final UserService userService;

  @GetMapping
  public List<User> getAll() {
    return userService.findAll();
  }

  @GetMapping("/{id}")
  public User get(@PathVariable String id) {
    return userService.findById(id);
  }

  @PostMapping
  @TenantContext(key = "#userCreateRequest.tenantId")
  public User save(@RequestBody UserCreateRequest userCreateRequest) {
    User user = User.builder()
        .tenantId(userCreateRequest.tenantId)
        .username(userCreateRequest.username)
        .build();

    return userService.create(user);
  }

  @PostMapping("/{id}/username")
  public User updateUsername(@PathVariable String id, String username) {
    return userService.updateUsername(id, username);
  }


  @Getter
  public static class UserCreateRequest {
    private String tenantId;
    private String username;

    @Builder
    public UserCreateRequest(String tenantId, String username) {
      this.tenantId = tenantId;
      this.username = username;
    }

  }
}
