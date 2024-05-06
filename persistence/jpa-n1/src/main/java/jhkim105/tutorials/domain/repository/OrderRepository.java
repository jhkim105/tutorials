package jhkim105.tutorials.domain.repository;

import jhkim105.tutorials.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {


  @Query("select o from Order o where o.id = :id")
  Order findByIdUsingQuery(@Param("id") Long id);

}
