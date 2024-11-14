package jhkim105.tutorials.jpatime;

import java.time.OffsetTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

  List<User> findByOffsetTimeBetween(OffsetTime from, OffsetTime to);
}
