package jhkim105.tutorials.springboot3.repository;


import static jhkim105.tutorials.springboot3.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jhkim105.tutorials.springboot3.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<User> getUsers() {
    return jpaQueryFactory.from(user)
        .select(user).fetch();
  }
}
