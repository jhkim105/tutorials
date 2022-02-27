package jhkim105.tutorials.spring.cloud.microservices.rating;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;

  public List<Rating> findAll() {
    return ratingRepository.findAll();
  }
  public List<Rating> findAllByBookId(Long bookId) {
    return ratingRepository.findAllByBookId(bookId);
  }

}
