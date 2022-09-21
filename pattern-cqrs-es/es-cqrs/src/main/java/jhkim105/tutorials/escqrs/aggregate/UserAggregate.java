package jhkim105.tutorials.escqrs.aggregate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import jhkim105.tutorials.escqrs.command.CreateUserCommand;
import jhkim105.tutorials.escqrs.command.DeleteUserCommand;
import jhkim105.tutorials.escqrs.command.UpdateUserCommand;
import jhkim105.tutorials.escqrs.event.Event;
import jhkim105.tutorials.escqrs.event.UserCreatedEvent;
import jhkim105.tutorials.escqrs.event.UserDeletedEvent;
import jhkim105.tutorials.escqrs.event.UserUpdatedEvent;
import jhkim105.tutorials.escqrs.repository.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAggregate {

  private final EventStore writeRepository;

  public Event handle(CreateUserCommand command) {
    String id = UUID.randomUUID().toString();
    UserCreatedEvent event = new UserCreatedEvent(id, command.getUsername());
    writeRepository.addEvent(id, event);
    return event;
  }

  public Event handle(UpdateUserCommand command) {
    UserUpdatedEvent event = new UserUpdatedEvent(command.getId(), command.getUsername());
    writeRepository.addEvent(command.getId(), event);
    return event;
  }

  public Event handle(DeleteUserCommand command) {
    UserDeletedEvent event = new UserDeletedEvent(command.getId());
    writeRepository.addEvent(command.getId(), event);
    return event;
  }

}