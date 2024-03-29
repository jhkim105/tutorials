package jhkim105.tutorials.spring_security_role.user;

import java.util.List;
import jhkim105.tutorials.spring_security_role.security.SecurityUtils;
import jhkim105.tutorials.spring_security_role.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


  private final UserService userService;

  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetails me() {
    return SecurityUtils.getCurrentUserDetails();
  }


  @GetMapping()
  public List<User> findAll() {
    return userService.findAll();
  }


}
