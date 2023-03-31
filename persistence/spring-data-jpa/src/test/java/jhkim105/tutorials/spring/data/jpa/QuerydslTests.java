package jhkim105.tutorials.spring.data.jpa;

import static jhkim105.tutorials.spring.data.jpa.domain.QUser.user;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import jhkim105.tutorials.spring.data.jpa.domain.User;
import jhkim105.tutorials.spring.data.jpa.projection.QUserProjection;
import jhkim105.tutorials.spring.data.jpa.projection.UserProjection;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class QuerydslTests {

  @Autowired
  JPAQueryFactory jpaQueryFactory;

  @Autowired
  EntityManager entityManager;



  @Test
  void map() {
    List<Tuple> list = jpaQueryFactory.select(user.id, user).from(user).fetch();
    Map<String, User> map = list.stream().collect(Collectors.toMap(tuple -> tuple.get(0, String.class), tuple -> tuple.get(1, User.class)));
    log.info("{}", map);
  }

  @Test
  void map2() {
    Map<String, User> map = jpaQueryFactory.from(user).transform(GroupBy.groupBy(user.id).as(user));
    log.info("{}", map);
  }

  @Test
  void groupByToMap() {
    Map<String, Long> map =jpaQueryFactory.from(user).groupBy(user.group).transform(GroupBy.groupBy(user.group.id).as(user.count()));
    log.info("{}", map);
  }

  @Test
  void groupByToTupleList() {
    List<Tuple> list =jpaQueryFactory.select(user.group.id, user.count()).from(user).groupBy(user.group).fetch();
    log.info("{}", list);
    log.info("{}", list.get(0).get(user.group.id));
    log.info("{}", list.get(0).get(user.count()));
  }

  @Test
  void projection() {
    JPAQuery<?> query = new JPAQuery<>(entityManager);
    UserProjection userProjection = query.select(new QUserProjection(user.id, user.username)).from(user).fetchFirst();
    log.info("{}", userProjection);
  }

  @Test
  void fetchFirst() {
    JPAQuery<User> query = jpaQueryFactory.selectFrom(user);
    User result = query.select(user).fetchFirst();
    log.info("{}", result);
  }

  @Test
  void count() {
    Long count = jpaQueryFactory.select(Wildcard.count).from(user).where(user.id.eq("non-exists-id")).fetchOne();
    log.info("{}", count);
  }

  @Test
  void stringExpression() {
    // select 'abc' as str, id, username from dm_user;
    StringExpression stringExpression = Expressions.stringTemplate("'aaa'");
    UserProjection userProjection = jpaQueryFactory.select( new QUserProjection(stringExpression, user.id, user.username)).from(user).fetchFirst();
    log.info("{}", userProjection);
  }

  @Test
  void stringExpression2() {
    // select 'abc' as str, id, username from dm_user;
    StringExpression stringExpression = Expressions.asString("aaa").as("str");
    UserProjection userProjection = jpaQueryFactory.select( new QUserProjection(stringExpression, user.id, user.username)).from(user).fetchFirst();
    log.info("{}", userProjection);
  }

  @Test
  void exists() {
    boolean exists = jpaQueryFactory.selectOne()
        .from(user).where(user.username.eq("non-exists-user")).fetchFirst() != null;

    assertThat(exists).isFalse();
  }

  @Test
  void expressionMethod() {
    jpaQueryFactory.selectOne()
        .from(user).where(eqUsername(null)).fetchFirst();

  }

  private BooleanExpression eqUsername(String username) {
    if (username == null) {
      return user.username.isNull();
    }

    return user.username.eq(username);
  }


}
