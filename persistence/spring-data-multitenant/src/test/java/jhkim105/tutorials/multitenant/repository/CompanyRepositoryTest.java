package jhkim105.tutorials.multitenant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenant.domain.Company;
import jhkim105.tutorials.multitenant.master.MasterDatabaseConfig;
import jhkim105.tutorials.multitenant.tenant.TenantDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MasterDatabaseConfig.class, TenantDatabaseConfig.class})
@Slf4j
@Transactional(transactionManager = "tenantTransactionManager")
public class CompanyRepositoryTest {

  @Autowired
  CompanyRepository companyRepository;

  @BeforeEach
  void setUp() {
    Company company = Company.builder().name("Company Test").build();
    companyRepository.save(company);
  }

  @Test
  @Sql(scripts = {"/company.sql"}, config = @SqlConfig(transactionManager = "tenantTransactionManager"))
//  @Transactional
  void test() {
    log.info(TransactionSynchronizationManager.getCurrentTransactionName());
    log.info("isActualTransactionActive: {}", TransactionSynchronizationManager.isActualTransactionActive());
    assertThat(companyRepository.getById("tid01")).isNotNull();
  }


  @Test
  void getByName() {
    assertThat(companyRepository.getByName("Company Test")).isNotNull();
  }

  @Test
  void deleteByName() {
    companyRepository.deleteByName("Company Test");
  }

}
