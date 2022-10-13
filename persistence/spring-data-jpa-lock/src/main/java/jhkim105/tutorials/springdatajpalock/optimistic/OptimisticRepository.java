package jhkim105.tutorials.springdatajpalock.optimistic;

import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import jhkim105.tutorials.springdatajpalock.pessimistic.Pessimistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OptimisticRepository extends JpaRepository<Optimistic, String> {

//  @Lock(LockModeType.OPTIMISTIC) //  @Lock(LockModeType.READ)
  Optional<Optimistic> findById(String id);


  @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT) //  @Lock(LockModeType.READ)
  Optional<Optimistic> findByCount(long count);

}
