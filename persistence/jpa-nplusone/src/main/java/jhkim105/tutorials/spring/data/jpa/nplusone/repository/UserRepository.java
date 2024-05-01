package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {


  // Many side
  @EntityGraph("User.couponsAndOrders")
  @Query("select o from User o")
  Page<User> findAllUsingEntityGraph(Pageable pageable);

  @Query("select u from User u join fetch u.orders join fetch u.coupons")
  List<User> findAllUsingQuery();

}
