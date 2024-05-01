package jhkim105.tutorials.spring.data.jpa.nplusone.repository;


import javax.persistence.EntityManager;
import jhkim105.tutorials.spring.data.jpa.nplusone.JpaConfig;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Order;
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
  @ExpectSelect(1)
  void find() {
    orderRepository.findById(-99L).orElseThrow();
  }

  @Test
  @ExpectSelect(2)
  void findUsingQuery() {
    orderRepository.findByIdUsingQuery(-99L);
  }

  @Test
  @ExpectSelect(1)
  void findAndUpdate() {
    Order order = orderRepository.findById(-99L).orElseThrow();
    order.complete();
    orderRepository.save(order);
    entityManager.flush();
    entityManager.clear();
  }

}
