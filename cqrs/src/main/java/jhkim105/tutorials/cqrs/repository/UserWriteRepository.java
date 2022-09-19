package jhkim105.tutorials.cqrs.repository;

import jhkim105.tutorials.cqrs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<User, String> {


}
