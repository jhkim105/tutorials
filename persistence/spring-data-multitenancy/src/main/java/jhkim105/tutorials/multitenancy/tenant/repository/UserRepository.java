package jhkim105.tutorials.multitenancy.tenant.repository;

import jhkim105.tutorials.multitenancy.tenant.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  User findByUsername(String username);
}
