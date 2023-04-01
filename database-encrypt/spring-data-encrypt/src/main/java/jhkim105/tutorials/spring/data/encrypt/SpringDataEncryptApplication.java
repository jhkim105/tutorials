package jhkim105.tutorials.spring.data.encrypt;

import java.util.ArrayList;
import java.util.List;
import jhkim105.tutorials.spring.data.encrypt.domain.User;
import jhkim105.tutorials.spring.data.encrypt.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDataEncryptApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringDataEncryptApplication.class, args);
  }


  @Bean
  public ApplicationRunner dataLoader(UserRepository userRepository) {
    return args -> {
      List<User> users = new ArrayList<>();
      users.add(User.builder().name("Name 1").username("username01").description("사용자 01").build());
      users.add(User.builder().name("Name 2").username("username02").description("사용자 02").build());
      users.add(User.builder().name("Name 3").username("username03").description("사용자 03").build());
      userRepository.saveAll(users);
    };
  }

  @Bean
  @ConfigurationProperties(prefix = "application")
  public ApplicationProperties applicationProperties() {
    return new ApplicationProperties();
  }



}
