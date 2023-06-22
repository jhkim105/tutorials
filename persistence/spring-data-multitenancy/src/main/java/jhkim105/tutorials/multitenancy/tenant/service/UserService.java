package jhkim105.tutorials.multitenancy.tenant.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.tenant.domain.User;
import jhkim105.tutorials.multitenancy.tenant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


  public User join(Tenant tenant, String username) {
    User user = User.builder()
        .username(username)
        .tenantId(tenant.getId()).build();
    return userRepository.save(user);
  }
  public User join(String username) {
    User user = User.builder().username(username).build();
    return userRepository.save(user);
  }

  public User findById(String id) {
    return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("User not found. id:%s", id)));
  }


  @Async
  public User updateUsername(String id, String username) {
    User user = userRepository.findById(id).get();
    user.setUsername(username);
    return userRepository.save(user);
  }

  public User create(User user) {
    return userRepository.save(user);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

}
