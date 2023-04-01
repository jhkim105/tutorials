package jhkim105.tutorials.spring.data.encrypt;

import java.util.ArrayList;
import java.util.List;
import jhkim105.tutorials.spring.data.encrypt.domain.User;
import jhkim105.tutorials.spring.data.encrypt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

  private final UserRepository userRepository;


  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<User> users = new ArrayList<>();
    users.add(User.builder().name("Name 1").username("username01").description("사용자 01").build());
    users.add(User.builder().name("Name 2").username("username02").description("사용자 02").build());
    users.add(User.builder().name("Name 3").username("username03").description("사용자 03").build());
    userRepository.saveAll(users);
  }
}
