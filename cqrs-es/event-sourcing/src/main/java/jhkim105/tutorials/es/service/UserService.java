package jhkim105.tutorials.es.service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import jhkim105.tutorials.es.event.UserCreatedEvent;
import jhkim105.tutorials.es.repository.EventStore;
import jhkim105.tutorials.es.domain.Address;
import jhkim105.tutorials.es.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final EventStore repository;


  public void createUser(String name) {
    repository.addEvent(UUID.randomUUID().toString(), new UserCreatedEvent(name));
  }


  public Set<Address> getAddressByName(String userId, String name) throws Exception {
    User user = UserUtility.recreateUserState(repository, userId);
    if (user == null)
      throw new Exception("User does not exist.");
    return user.getAddresses()
        .stream()
        .filter(a -> a.getName()
            .equals(name))
        .collect(Collectors.toSet());
  }

}
