package jhkim105.tutorials.spring_security_role.user.repository;

import jhkim105.tutorials.spring_security_role.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);
}
