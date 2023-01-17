package jhkim105.tutorials.springboot3.repository;

import jhkim105.tutorials.springboot3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {

  User findByUsername(String username);
  boolean existsByUsername(String username);
}
