package jhkim105.tutorials.spring.data.jpa.reposiotry;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.BaseRepositoryTest;
import jhkim105.tutorials.spring.data.jpa.domain.Order;
import jhkim105.tutorials.spring.data.jpa.projection.OrderProjection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

@Slf4j
class OrderRepositoryTest extends BaseRepositoryTest {

  @Autowired
  OrderRepository orderRepository;

  @Test
  void example() {
    String searchKeyword = "주문";
    Order order = Order.builder().name(searchKeyword).build();
    ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
        .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().startsWith());
    Example<Order> example = Example.of(order, exampleMatcher);
    List<Order> orderList = orderRepository.findAll(example);
    assertThat(orderList).hasSizeGreaterThan(0);
  }


  @Test
  void projection() {
    List<OrderProjection> list = orderRepository.getOrderProjections();
    log.info("{}", list);
  }

}
