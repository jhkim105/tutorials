package jhkim105.tutorials.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.JpaConfig;
import org.junit.jupiter.api.Test;
import org.quickperf.spring.sql.QuickPerfSqlConfig;
import org.quickperf.sql.annotation.ExpectSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaConfig.class, QuickPerfSqlConfig.class})
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @ExpectSelect(1)
  // 참조관계 OneToOne 은 Lazy 여도 연관쿼리 발생함
  void findById() {
    userRepository.findById("id01");
  }


  @Test
  @ExpectSelect(7) // 참조관계 OneToOne, OneToMany Eager n+1 발생
  void findAll() {
    userRepository.findAll();
  }

  @Test
  void findAllPage() {
    userRepository.findAllPage(PageRequest.of(0, 2));
  }


  @Test
  @ExpectSelect(2) // paging 쿼리 없음(limit). 메모리에 다 불러와서 paging 함
  void findAllPageUsingEntityGraph() {
    var pageSize = 2;
    var page = userRepository.findAllPageWithOrdersAndCouponsUsingEntityGraph(PageRequest.of(0, pageSize));
    assertThat(page.getContent().size()).isEqualTo(pageSize);
  }

  @Test
  @ExpectSelect(1) // paging 쿼리 없음(limit). 한건만 조회함
  void findAllPageUsingFetchJoin() {
    var pageSize = 2;
    var page = userRepository.findAllPageWithOrdersAndCouponsUsingFetchJoin(PageRequest.of(0, pageSize));
    assertThat(page.getContent().size()).isEqualTo(1);
  }

}
