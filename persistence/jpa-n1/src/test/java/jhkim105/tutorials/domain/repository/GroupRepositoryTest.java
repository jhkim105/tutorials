package jhkim105.tutorials.domain.repository;

import jhkim105.tutorials.JpaConfig;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaConfig.class, QuickPerfSqlConfig.class})
class GroupRepositoryTest {

  @Autowired
  GroupRepository groupRepository;


  @Test
  @ExpectSelect(2) // OneToOne LAZY 인 경우 n+1
  void findOne() {
    groupRepository.findById("id01");
  }
  @Test
  @ExpectSelect(3) // OneToOne LAZY, 데이터가 없는 경우 2n+1 (버그?)
  void findOne_2n_plus_1() {
    groupRepository.findById("id02");
  }

  @Test
  @ExpectSelect(4) // OneToOne LAZY, 데이터가 없는 경우 2n+1 (버그?) AW
  void findAll() {
    groupRepository.findAll();
  }


}