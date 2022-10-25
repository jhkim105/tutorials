package jhkim105.tutorials.multitenancy.service;

import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final TenantRepository tenantRepository;
  private final TenantService tenantService;

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
    tenantService.updateTenantName(user.getTenantId(), username);
    user.setUsername(username);
    user = userRepository.save(user);
    return user;
  }
}
