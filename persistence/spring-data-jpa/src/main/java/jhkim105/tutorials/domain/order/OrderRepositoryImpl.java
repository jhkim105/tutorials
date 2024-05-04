package jhkim105.tutorials.domain.order;


import static jhkim105.tutorials.domain.order.QOrder.order;
import static jhkim105.tutorials.domain.user.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
        .select(new QOrderProjection(order.name, user.username)).fetch();
  }
}
