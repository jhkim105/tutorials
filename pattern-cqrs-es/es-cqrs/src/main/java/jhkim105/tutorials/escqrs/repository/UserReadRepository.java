package jhkim105.tutorials.escqrs.repository;

import java.util.Optional;
import jhkim105.tutorials.escqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadRepository extends JpaRepository<User, String> {

  Optional<User> findByUsername(String username);

}
