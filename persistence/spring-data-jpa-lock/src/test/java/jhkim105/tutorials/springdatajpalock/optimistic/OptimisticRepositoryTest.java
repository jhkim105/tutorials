package jhkim105.tutorials.springdatajpalock.optimistic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Slf4j
class OptimisticRepositoryTest {

  @Autowired
  OptimisticRepository optimisticRepository;


  @Test
  void findById() {
    Optimistic optimistic = optimisticRepository.findById("id01").get();
    optimistic.incr();
    optimistic = optimisticRepository.save(optimistic);
    assertThat(optimistic.getVersion()).isEqualTo(2);
  }



}