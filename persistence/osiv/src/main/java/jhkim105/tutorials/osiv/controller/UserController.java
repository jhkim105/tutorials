package jhkim105.tutorials.osiv.controller;

import jhkim105.tutorials.osiv.domain.User;
import jhkim105.tutorials.osiv.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService service;


  @GetMapping("/{id}")
  public User get(@PathVariable String id) {
    User user = service.get(id);
    log.info("user->[{}]", user);
    return service.get(id);
  }

  @GetMapping("/entity-graph/{id}")
  public User getUsingEntityGraph(@PathVariable String id) {
    User user = service.getUsingEntityGraph(id);
    log.info("user->[{}]", user);
    return user;
  }

  @GetMapping("/fetch-join/{id}")
  public User getUsingFetchJoin(@PathVariable String id) {
    User user = service.getUsingFetchJoin(id);
    log.info("user->[{}]", user);
    return user;
  }

}
