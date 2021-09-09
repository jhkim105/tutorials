package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {



}
