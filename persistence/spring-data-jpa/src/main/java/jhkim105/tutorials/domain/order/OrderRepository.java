package jhkim105.tutorials.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String>, OrderRepositoryCustom {

}
