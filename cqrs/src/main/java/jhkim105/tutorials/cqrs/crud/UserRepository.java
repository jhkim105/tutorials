package jhkim105.tutorials.cqrs.crud;

import jhkim105.tutorials.cqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
