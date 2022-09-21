package jhkim105.tutorials.cqrs.controller;

import jhkim105.tutorials.cqrs.domain.User;
import jhkim105.tutorials.cqrs.projection.UserProjection;
import jhkim105.tutorials.cqrs.query.UserByUsernameQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserQueryController {

  private final UserProjection userProjection;

  @GetMapping("/by-username")
  public User queryByName(UserByUsernameQuery query) {
    return userProjection.handle(query);
  }

}

