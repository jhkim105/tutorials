package jhkim105.tutorials.multitenancy.repository;

import jhkim105.tutorials.multitenancy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {

  User findByUsername(String username);
}
