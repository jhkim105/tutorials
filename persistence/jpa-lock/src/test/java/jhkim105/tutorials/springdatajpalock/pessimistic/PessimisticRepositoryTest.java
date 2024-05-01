package jhkim105.tutorials.springdatajpalock.pessimistic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PessimisticRepositoryTest {

  @Autowired
  PessimisticRepository pessimisticRepository;


  @Test
  void findById() {
    pessimisticRepository.findById("id01");
  }

}