package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Invest;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.InvestProjection;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.QInvest;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.QInvestProjection;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.QProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class InvestRepositoryImpl implements InvestRepositoryCustom{

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<Invest> findAllByUserIdUsingQueryDsl(Pageable pageable, String userId) {
    QInvest invest = QInvest.invest;
    JPAQuery<Invest> query = jpaQueryFactory.selectFrom(invest);
    query.join(invest.product, QProduct.product).fetch();
    query.where(invest.userId.eq(userId));

    if(pageable.isPaged()) {
      query.offset(pageable.getOffset());
      query.limit(pageable.getPageSize());
    }

    List<Invest> invests = query.fetch();

    return new PageImpl<>(invests, pageable, query.fetchCount());
  }

  public Page<InvestProjection> findAllByUserIdUsingQueryDslProjection(Pageable pageable, String userId) {
    QInvest invest = QInvest.invest;
    QProduct product = QProduct.product;

    JPAQuery<InvestProjection> query = new JPAQuery<>();
    query.from(invest)
        .join(invest.product, product)
        .where(invest.userId.eq(userId));
//    query.join(invest.product, product).fetch();
//    query.where(invest.userId.eq(userId));
//
//    if(pageable.isPaged()) {
//      query.offset(pageable.getOffset());
//      query.limit(pageable.getPageSize());
//    }

    List<InvestProjection> invests =
        query.select(new QInvestProjection(invest.userId, invest.id, product.id, product.title, invest.amount)).fetch();

    return new PageImpl<>(invests, pageable, query.fetchCount());
  }
}
