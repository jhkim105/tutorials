package com.example.demo.reposiotry;

import com.example.demo.domain.QOrder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public void update() {
    jpaQueryFactory.update(QOrder.order).set(QOrder.order.name, "New Name").execute();
  }
}
