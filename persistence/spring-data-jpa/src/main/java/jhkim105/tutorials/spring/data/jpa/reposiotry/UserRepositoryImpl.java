package jhkim105.tutorials.spring.data.jpa.reposiotry;

import static jhkim105.tutorials.spring.data.jpa.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import jhkim105.tutorials.spring.data.jpa.domain.User;
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
