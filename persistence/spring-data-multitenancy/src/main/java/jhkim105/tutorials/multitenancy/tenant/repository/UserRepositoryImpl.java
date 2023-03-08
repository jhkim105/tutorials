package jhkim105.tutorials.multitenancy.tenant.repository;


import static jhkim105.tutorials.multitenancy.tenant.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

//@Transactional
@Transactional(transactionManager = "tenantTransactionManager")
public class UserRepositoryImpl implements UserRepositoryCustom {

  @Resource(name = "tenantQueryFactory")
  JPAQueryFactory jpaQueryFactory;


  @Override
  public void deleteByUsername(String username) {
    jpaQueryFactory.delete(user).where(user.username.eq(username)).execute();
  }
}
