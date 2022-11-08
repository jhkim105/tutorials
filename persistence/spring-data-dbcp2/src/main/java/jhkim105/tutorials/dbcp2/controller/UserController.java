package jhkim105.tutorials.dbcp2.controller;

import java.util.List;
import jhkim105.tutorials.dbcp2.domain.User;
import jhkim105.tutorials.dbcp2.service.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserManager userManager;


  @GetMapping
  public List<User> findAll() {
    return userManager.findAllLeakCaused();
  }


}
