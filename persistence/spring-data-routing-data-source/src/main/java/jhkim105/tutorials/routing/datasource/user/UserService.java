package jhkim105.tutorials.routing.datasource.user;

import static java.lang.Thread.sleep;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @SneakyThrows
  @Transactional(readOnly = true)
  public List<User> findAll() {
    sleep(1000);
    return userRepository.findAll();
  }

}
