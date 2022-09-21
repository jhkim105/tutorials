package jhkim105.tutorials.es.service;

import java.util.List;
import java.util.UUID;
import jhkim105.tutorials.es.domain.User;
import jhkim105.tutorials.es.event.Event;
import jhkim105.tutorials.es.event.UserCreatedEvent;
import jhkim105.tutorials.es.event.UserDeletedEvent;
import jhkim105.tutorials.es.event.UserUpdatedEvent;
import jhkim105.tutorials.es.repository.EventStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final EventStore repository;


  public String createUser(String username) {
    String userId = UUID.randomUUID().toString();
    repository.addEvent(userId, new UserCreatedEvent(userId, username));
    return userId;
  }

  public void updateUser(String userId, String username) {
    repository.addEvent(userId, new UserUpdatedEvent(userId, username));
  }

  public void deleteUser(String userId) {
    repository.addEvent(userId, new UserDeletedEvent(userId));
  }

  public User get(String userId) {
    List<Event> events = repository.getEvents(userId);
    User user = null;
    for (Event event : events) {
      if (event instanceof UserCreatedEvent) {
        UserCreatedEvent e = (UserCreatedEvent) event;
        user = new User(userId, e.getUsername());
      }

      if (event instanceof UserUpdatedEvent) {
        UserUpdatedEvent e = (UserUpdatedEvent) event;
        user = new User(userId, e.getUsername());
      }

      if (event instanceof UserDeletedEvent) {
        user = null;
      }

    }

    return user;
  }

}
