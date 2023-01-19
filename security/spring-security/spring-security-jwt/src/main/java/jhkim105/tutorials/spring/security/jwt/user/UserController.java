package jhkim105.tutorials.spring.security.jwt.user;

import jhkim105.tutorials.spring.security.jwt.security.SecurityUtils;
import jhkim105.tutorials.spring.security.jwt.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/me")
  public UserPrincipal me() {
    UserPrincipal userPrincipal = SecurityUtils.getAuthUser();
    return userPrincipal;
  }


  @PostMapping
  public User save(@RequestBody UserUpdateRequest userUpdateRequest) {
    User currentUser = userService.getCurrentUser();
    userUpdateRequest.applyTo(currentUser);
    return userService.save(currentUser);
  }

}
