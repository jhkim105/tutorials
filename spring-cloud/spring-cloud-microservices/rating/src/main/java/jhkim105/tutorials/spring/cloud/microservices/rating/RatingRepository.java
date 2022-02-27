package jhkim105.tutorials.spring.cloud.microservices.rating;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {

  List<Rating> findAllByBookId(Long bookId);
}
