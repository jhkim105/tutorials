package jhkim105.tutorials.springdatajpalock.optimistic;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface OptimisticRepository extends JpaRepository<Optimistic, String> {

  Optional<Optimistic> findById(String id);

  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
  Optional<Optimistic> findByName(String name);

}
