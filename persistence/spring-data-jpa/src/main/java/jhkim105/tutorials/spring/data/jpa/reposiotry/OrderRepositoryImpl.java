package jhkim105.tutorials.spring.data.jpa.reposiotry;

import static jhkim105.tutorials.spring.data.jpa.domain.QOrder.order;
import static jhkim105.tutorials.spring.data.jpa.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jhkim105.tutorials.spring.data.jpa.projection.OrderProjection;
import jhkim105.tutorials.spring.data.jpa.projection.QOrderProjection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void update() {
    jpaQueryFactory.update(order).set(order.name, "New Name").execute();
  }

  @Override
  public List<OrderProjection> getOrderProjections() {
    return jpaQueryFactory.from(order)
        .join(order.user, user)
        .select(new QOrderProjection(order.name, user.name)).fetch();
  }
}
