package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Invest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvestRepository extends JpaRepository<Invest, Long>, InvestRepositoryCustom{
  @EntityGraph(attributePaths = "product")
  Page<Invest> findAllByUserId(Pageable pageable, String userId);


//  @Query("select o from Invest o join fetch o.product where o.userId = :userId")
//  @Query("select o from Invest o join fetch o.product where o.userId = :userId")
//  Page<Invest> findAllByUserId(Pageable pageable, @Param("userId") String userId);

  @Query("select o from Invest o where o.userId = :userId")
  List<Invest> findAllByUserIdUsingQuery(@Param("userId") String userId);


}
