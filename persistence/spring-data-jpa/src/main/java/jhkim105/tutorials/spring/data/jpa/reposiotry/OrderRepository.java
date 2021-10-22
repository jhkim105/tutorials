package jhkim105.tutorials.spring.data.jpa.reposiotry;

import jhkim105.tutorials.spring.data.jpa.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String>, OrderRepositoryCustom {

}
