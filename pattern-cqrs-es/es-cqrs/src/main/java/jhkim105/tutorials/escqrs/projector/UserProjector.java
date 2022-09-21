package jhkim105.tutorials.escqrs.projector;

import java.util.List;
import jhkim105.tutorials.escqrs.domain.User;
import jhkim105.tutorials.escqrs.event.Event;
import jhkim105.tutorials.escqrs.event.UserCreatedEvent;
import jhkim105.tutorials.escqrs.event.UserDeletedEvent;
import jhkim105.tutorials.escqrs.event.UserUpdatedEvent;
import jhkim105.tutorials.escqrs.repository.UserReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProjector {

  private final UserReadRepository readRepository;

  // write from EventStore to ReadRepository
  public void project(String userId, Event event) {
    if (event instanceof UserCreatedEvent) {
      apply(userId, (UserCreatedEvent)event);
    }

    if (event instanceof UserUpdatedEvent) {
      apply(userId, (UserUpdatedEvent)event);
    }

    if (event instanceof UserDeletedEvent) {
      apply(userId, (UserDeletedEvent)event);
    }

  }

  private void apply(String userId, UserCreatedEvent event) {
    User user = new User(userId, event.getUsername());
    readRepository.save(user);
  }

  private void apply(String userId, UserUpdatedEvent event) {
    User user = readRepository.findById(userId).get();
    user.setUsername(event.getUsername());
    readRepository.save(user);
  }

  private void apply(String userId, UserDeletedEvent event) {
    readRepository.deleteById(userId);
  }


}
