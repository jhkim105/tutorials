package jhkim105.tutorials.cqrs.repository;

import java.util.Optional;
import jhkim105.tutorials.cqrs.domain.User;

public interface UserReadRepository extends ReadOnlyRepository<User, String> {

  Optional<User> findByUsername(String username);
}
