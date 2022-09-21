package jhkim105.tutorials.escqrs.controller;

import java.util.Arrays;
import jhkim105.tutorials.escqrs.aggregate.UserAggregate;
import jhkim105.tutorials.escqrs.command.CreateUserCommand;
import jhkim105.tutorials.escqrs.command.DeleteUserCommand;
import jhkim105.tutorials.escqrs.command.UpdateUserCommand;
import jhkim105.tutorials.escqrs.domain.User;
import jhkim105.tutorials.escqrs.event.Event;
import jhkim105.tutorials.escqrs.event.UserCreatedEvent;
import jhkim105.tutorials.escqrs.event.UserDeletedEvent;
import jhkim105.tutorials.escqrs.event.UserUpdatedEvent;
import jhkim105.tutorials.escqrs.projector.UserProjection;
import jhkim105.tutorials.escqrs.projector.UserProjector;
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
  private final UserProjector userProjector;

  @PostMapping
  public void create(@RequestBody CreateUserCommand command) {
    UserCreatedEvent event = (UserCreatedEvent)userAggregate.handle(command);
    userProjector.project(event.getUserId(), event);
  }

  @PutMapping
  public void update(@RequestBody UpdateUserCommand command) {
    UserUpdatedEvent event = (UserUpdatedEvent)userAggregate.handle(command);
    userProjector.project(event.getUserId(), event);
  }

  @DeleteMapping
  public void  delete(@RequestBody DeleteUserCommand command) {
    UserDeletedEvent event = (UserDeletedEvent)userAggregate.handle(command);
    userProjector.project(event.getUserId(), event);
  }

}
