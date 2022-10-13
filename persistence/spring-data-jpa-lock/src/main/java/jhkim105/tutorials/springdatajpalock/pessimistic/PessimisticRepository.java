package jhkim105.tutorials.springdatajpalock.pessimistic;

import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface PessimisticRepository extends JpaRepository<Pessimistic, String> {

  @Lock(LockModeType.PESSIMISTIC_READ)
//  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Pessimistic> findById(String id);
}
