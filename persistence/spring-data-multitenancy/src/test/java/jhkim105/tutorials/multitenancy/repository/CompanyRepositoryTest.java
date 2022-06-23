package jhkim105.tutorials.multitenancy.repository;

import static org.assertj.core.api.Assertions.assertThat;

import jhkim105.tutorials.multitenancy.domain.Company;
import jhkim105.tutorials.multitenancy.master.MasterDatabaseConfig;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@ActiveProfiles("test")
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
  @Sql(statements = {"DELETE FROM demo_multitenant_master.company WHERE id = 'tid01';"},
      config = @SqlConfig(transactionManager = "tenantTransactionManager"),
      executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
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
