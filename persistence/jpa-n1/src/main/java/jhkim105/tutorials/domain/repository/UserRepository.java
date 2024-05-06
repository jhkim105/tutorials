package jhkim105.tutorials.domain.repository;

import java.util.List;
import jhkim105.tutorials.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

  @Query("select u from User u")
  Page<User> findAllPage(Pageable pageable);

  @EntityGraph("User.orders")
  @Query("select o from User o")
  List<User> findAllWithOrdersUsingEntityGraph();

  @EntityGraph("User.ordersAndCoupons")
  @Query("select o from User o")
  List<User> findAllWithOrdersAndCouponsUsingEntityGraph();

  @Query("select u from User u join fetch u.orders join fetch u.coupons")
  List<User> findAllWithOrdersAndCouponsUsingFetchJoin();

  @EntityGraph("User.ordersAndCoupons")
  @Query("select o from User o")
  // paging query 없음 (limit)
  // 성능 이슈
  Page<User> findAllPageWithOrdersAndCouponsUsingEntityGraph(Pageable pageable);


  @Query("select u from User u join fetch u.orders join fetch u.coupons left join fetch u.membership")
  // paging query 없음 (limit)
  // 한건만 조회됨
  Page<User> findAllPageWithOrdersAndCouponsUsingFetchJoin(Pageable pageable);
}
