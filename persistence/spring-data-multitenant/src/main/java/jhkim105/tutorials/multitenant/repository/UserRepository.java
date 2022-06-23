package jhkim105.tutorials.multitenant.repository;

import jhkim105.tutorials.multitenant.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
