package jhkim105.tutorials.osiv.controller;

import jhkim105.tutorials.osiv.domain.User;
import jhkim105.tutorials.osiv.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/osiv")
@RequiredArgsConstructor
@Slf4j
public class UserOsivController {

  private final UserService service;


  @GetMapping("/{id}")
  public User get(@PathVariable String id) {
    return service.get(id);
  }

  @PostMapping
  public User create(String username) {
    User user = service.create(username);
    user.setUsername(username + "_modified");
    return service.getTransactional(user.getId()); // 의도하지 않은 update 문이 실행된다.
  }

}
