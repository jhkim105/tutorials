package jhkim105.tutorials.spring.data.encrypt.repository;

import java.util.List;
import jhkim105.tutorials.spring.data.encrypt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);
  List<User> findByDescriptionStartsWith(String search);

  List<User> findByNameStartsWith(String search);
}
