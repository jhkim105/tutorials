package jhkim105.tutorials.spring.data.jpa.nplusone.repository;

import java.util.List;
import jhkim105.tutorials.spring.data.jpa.nplusone.JpaConfig;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.Invest;
import jhkim105.tutorials.spring.data.jpa.nplusone.domain.InvestProjection;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class)
class InvestRepositoryTest {

  @Autowired
  private InvestRepository investRepository;

  @Test
  @Sql(scripts = {"/sql/products.sql", "/sql/invests.sql"})
  void findAllByUserId() {
    Page<Invest> investPage = investRepository.findAllByUserId(PageRequest.of(0, 2), "user01");
    Assertions.assertThat(investPage.getContent()).size().isEqualTo(2);
    Assertions.assertThat(investPage.getContent().get(0).getId()).isEqualTo(-99L);
  }

//  @Test
//  @Sql(scripts = {"/sql/products.sql", "/sql/invests.sql"})
//  void findAllByUserIdUsingQuery_exception_when_paging() {
//    Page<Invest> investPage = investRepository.findAllByUserIdUsingQuery(PageRequest.of(0, 2), "user01");
//    Assertions.assertThat(investPage.getContent()).size().isEqualTo(2);
//    Assertions.assertThat(investPage.getContent().get(0).getId()).isEqualTo(-99L);
//  }
//
  @Test
  @Sql(scripts = {"/sql/products.sql", "/sql/invests.sql"})
  void findAllByUserIdUsingQuery() {
    List<Invest> invests = investRepository.findAllByUserIdUsingQuery("user01");
  }

  @Test
  @Sql(scripts = {"/sql/products.sql", "/sql/invests.sql"})
  void findAllByUserIdUsingQueryDsl() {
    Page<Invest> investPage = investRepository.findAllByUserIdUsingQueryDsl(PageRequest.of(0, 2), "user01");
    Assertions.assertThat(investPage.getContent()).size().isEqualTo(2);
    Assertions.assertThat(investPage.getContent().get(0).getId()).isEqualTo(-99L);
  }

  @Test
  @Sql(scripts = {"/sql/products.sql", "/sql/invests.sql"})
  void findAllByUserIdUsingQueryDslProjection() {
    Page<InvestProjection> investPage = investRepository.findAllByUserIdUsingQueryDslProjection(PageRequest.of(0, 2), "user01");
    Assertions.assertThat(investPage.getContent()).size().isEqualTo(2);
    Assertions.assertThat(investPage.getContent().get(0).getInvestId()).isEqualTo(-99L);
  }

}