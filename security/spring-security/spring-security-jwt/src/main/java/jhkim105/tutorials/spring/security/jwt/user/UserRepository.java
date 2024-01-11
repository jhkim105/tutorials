package jhkim105.tutorials.spring.security.jwt.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
  User findByUsername(String username);
}
