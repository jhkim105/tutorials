package jhkim105.tutorials.springdatajpalock.optimistic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@Slf4j
@Disabled
class OptimisticRepositoryTest {

  @Autowired
  OptimisticRepository optimisticRepository;


  @Test
    // LockModeType.OPTIMISTIC (LockModeType.READ)
  void findById_then_increaseVersion() {
    Optimistic optimistic = optimisticRepository.findById("id01").get();
    optimistic.incr();
    optimistic = optimisticRepository.save(optimistic);
    assertThat(optimistic.getVersion()).isEqualTo(2);
  }


  @Test
  @Transactional
  // LockModeType.OPTIMISTIC_FORCE_INCREMENT(LockModeType.WRITE)
  void findByName_then_increaseVersion() {
    Optimistic optimistic2 = optimisticRepository.findByName("name02").get();
    optimisticRepository.flush();
    assertThat(optimistic2.getVersion()).isEqualTo(2);
  }


}