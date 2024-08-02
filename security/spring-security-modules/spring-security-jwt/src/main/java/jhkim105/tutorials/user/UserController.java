package jhkim105.tutorials.user;

import jhkim105.tutorials.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {

  private final UserService userService;


  @GetMapping("/me")
  public UserPrincipal me(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return userPrincipal;
  }


  @PostMapping
  public User save(@RequestBody UserUpdateRequest userUpdateRequest) {
    User currentUser = userService.getCurrentUser();
    userUpdateRequest.applyTo(currentUser);
    return userService.save(currentUser);
  }

}
