package jhkim105.tutorials.controller;


import java.util.List;
import jhkim105.tutorials.domain.User;
import jhkim105.tutorials.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;


  @GetMapping
  public List<User> findAll() {
    return userService.findAllLeakCaused();
  }

  @GetMapping("/readonly")
  public void readOnly() {
    userService.doInReadOnlyTransaction();
  }

}
