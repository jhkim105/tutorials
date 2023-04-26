package jhkim105.tutorials.spring_security_role.user;


import jakarta.annotation.security.RolesAllowed;
import java.util.List;
import jhkim105.tutorials.spring_security_role.user.domain.User;
import jhkim105.tutorials.spring_security_role.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;


  @Secured("ROLE_ADMIN")
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @RolesAllowed("ROLE_ADMIN")
  public List<User> findAll2() {
    return userRepository.findAll();
  }

}
