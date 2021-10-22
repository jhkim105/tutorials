package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Invest;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.InvestProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface InvestRepositoryCustom {
  Page<Invest> findAllByUserIdUsingQueryDsl(Pageable pageable, @Param("userId") String userId);
  Page<InvestProjection> findAllByUserIdUsingQueryDslProjection(Pageable pageable, @Param("userId") String userId);
}
