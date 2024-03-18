package jhkim105.tutorials.service;


import jhkim105.tutorials.domain.User;
import jhkim105.tutorials.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;



  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }


}
