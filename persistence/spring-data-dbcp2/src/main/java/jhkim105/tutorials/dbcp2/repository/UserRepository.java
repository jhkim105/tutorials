package jhkim105.tutorials.dbcp2.repository;

import jhkim105.tutorials.dbcp2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
