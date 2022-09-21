package jhkim105.tutorials.cqrs.controller;

import jhkim105.tutorials.cqrs.aggregate.UserAggregate;
import jhkim105.tutorials.cqrs.command.CreateUserCommand;
import jhkim105.tutorials.cqrs.command.DeleteUserCommand;
import jhkim105.tutorials.cqrs.command.UpdateUserCommand;
import jhkim105.tutorials.cqrs.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserCommandController {

  private final UserAggregate userAggregate;

  @PostMapping
  public User create(@RequestBody CreateUserCommand command) {
    return userAggregate.handle(command);
  }

  @PutMapping
  public User update(@RequestBody UpdateUserCommand command) {
    return userAggregate.handle(command);
  }

  @DeleteMapping
  public void  delete(@RequestBody DeleteUserCommand command) {
    userAggregate.handle(command);
  }

}
