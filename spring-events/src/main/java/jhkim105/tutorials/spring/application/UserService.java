package jhkim105.tutorials.spring.application;


import jhkim105.tutorials.spring.domain.User;
import jhkim105.tutorials.spring.domain.UserRepository;
import jhkim105.tutorials.spring.events.CustomSpringEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Transactional
  public User save(User user) {
    user = userRepository.save(user);
    applicationEventPublisher.publishEvent(new CustomSpringEvent(this, "user created"));
    return user;
  }


}
