package jhkim105.tutorials.spring.data.jpa.reposiotry;

import jhkim105.tutorials.spring.data.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
