package jhkim105.tutorials.spring.data.encrypt;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

  private final UserRepository userRepository;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<User> users = new ArrayList<>();
    users.add(User.builder().name("Name 1").username("username01").phoneNumber("01011112222").build());
    users.add(User.builder().name("Name 2").username("username02").phoneNumber("01011112223").build());
    users.add(User.builder().name("Name 3").username("username03").phoneNumber("01011112223").build());
    userRepository.saveAll(users);
  }
}
