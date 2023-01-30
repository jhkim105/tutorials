package jhkim105.tutorials.spring_security_role;

import java.util.Arrays;
import jhkim105.tutorials.spring_security_role.user.domain.Role;
import jhkim105.tutorials.spring_security_role.user.domain.User;
import jhkim105.tutorials.spring_security_role.user.repository.RoleRepository;
import jhkim105.tutorials.spring_security_role.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringSecurityRoleApplication {

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SpringSecurityRoleApplication.class, args);
  }

  @Bean
  public ApplicationRunner loadData() {
    return args -> {
      Role adminRole = roleRepository.findByName("ROLE_ADMIN");
      userRepository.save(User.builder()
          .username("admin")
          .password(passwordEncoder.encode("admin"))
          .roles(Arrays.asList(adminRole))
          .build());

      Role userRole = roleRepository.findByName("ROLE_USER");
      userRepository.save(User.builder()
          .username("user01")
          .password(passwordEncoder.encode("pass01"))
          .roles(Arrays.asList(userRole))
          .build());

    };
  }

}
