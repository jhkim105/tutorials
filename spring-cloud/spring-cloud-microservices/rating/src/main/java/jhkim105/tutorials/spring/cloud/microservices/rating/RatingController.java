package jhkim105.tutorials.spring.cloud.microservices.rating;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

  private final RatingService ratingService;

  @GetMapping
  public List<Rating> findAllBooks(@RequestParam(required = false) Optional<Long> bookId) {

    return bookId.map(ratingService::findAllByBookId).orElseGet(ratingService::findAll);
  }

}
