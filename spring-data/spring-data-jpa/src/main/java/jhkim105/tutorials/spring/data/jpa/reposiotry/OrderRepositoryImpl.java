package jhkim105.tutorials.spring.data.jpa.reposiotry;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jhkim105.tutorials.spring.data.jpa.domain.QOrder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void update() {
    jpaQueryFactory.update(QOrder.order).set(QOrder.order.name, "New Name").execute();
  }
}
