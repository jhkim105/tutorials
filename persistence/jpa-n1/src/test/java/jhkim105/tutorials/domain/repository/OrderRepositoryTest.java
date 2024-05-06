package jhkim105.tutorials.domain.repository;


import jakarta.persistence.EntityManager;
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
public class OrderRepositoryTest {

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  @ExpectSelect(1) // EAGER 여서 join query 실행
  void find() {
    orderRepository.findById(-99L).orElseThrow();
  }

  @Test
  @ExpectSelect(2) // global fetch 전략을 따르지 않음. n+1 select 발생
  void findUsingQuery() {
    orderRepository.findByIdUsingQuery(-99L);
  }


}
