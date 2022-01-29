package jhkim105.tutorials.spring.data.envers.repository;

import jhkim105.tutorials.spring.data.envers.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "orders", path = "orders")
public interface OrderRepository extends JpaRepository<Order, String> {

}
