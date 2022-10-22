package jhkim105.tutorials.springdatajpalock.pessimistic;

import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PessimisticService {

  private final PessimisticRepository pessimisticRepository;

  @Transactional
  public void increase(String id) {
    Pessimistic pessimistic = pessimisticRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    pessimistic.incr();
    pessimisticRepository.save(pessimistic);
  }

}
