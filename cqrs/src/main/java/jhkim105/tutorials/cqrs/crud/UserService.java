package jhkim105.tutorials.cqrs.crud;


import java.util.Set;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.cqrs.domain.Address;
import jhkim105.tutorials.cqrs.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User get(String id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  public User createUser(String name) {
    User user = User.builder()
        .name("user 01")
        .build();
    return userRepository.save(user);
  }

  @Transactional
  public User updateUser(String id, Set<Address> addresses) {
    User user = get(id);
    user.setAddresses(addresses);
    return userRepository.save(user);
  }


}
