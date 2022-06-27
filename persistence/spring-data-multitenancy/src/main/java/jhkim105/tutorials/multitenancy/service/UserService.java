package jhkim105.tutorials.multitenancy.service;

import jhkim105.tutorials.multitenancy.domain.User;
import jhkim105.tutorials.multitenancy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User join(String username) {
    User user = User.builder().username(username).build();
    return userRepository.save(user);
  }

}
