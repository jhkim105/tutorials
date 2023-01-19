package jhkim105.tutorials.spring.security.jwt.user;

import java.util.Optional;
import jhkim105.tutorials.spring.security.jwt.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    String userId = SecurityUtils.getAuthUser().getId();
    Optional<User> optionalUser = userRepository.findById(userId);
    optionalUser.orElseThrow(RuntimeException::new);
    return optionalUser.get();
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }
}
