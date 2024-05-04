package jhkim105.tutorials.domain.user;


import static jhkim105.tutorials.domain.user.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
