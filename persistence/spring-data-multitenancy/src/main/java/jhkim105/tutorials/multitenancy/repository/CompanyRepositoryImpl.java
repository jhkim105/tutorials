package jhkim105.tutorials.multitenancy.repository;

import static jhkim105.tutorials.multitenancy.domain.QCompany.company;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.annotation.Resource;

//@Transactional(transactionManager = "tenantTransactionManager")
public class CompanyRepositoryImpl implements CompanyRepositoryCustom{

  @Resource(name = "tenantQueryFactory")
  JPAQueryFactory jpaQueryFactory;


  @Override
  public void deleteByName(String name) {
    jpaQueryFactory.delete(company).where(company.name.eq(name)).execute();
  }
}
