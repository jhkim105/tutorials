package com.example.multitenant.repository;

import com.example.multitenant.master.MasterDatabaseConfig;
import com.example.multitenant.tenant.TenantDatabaseConfig;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MasterDatabaseConfig.class, TenantDatabaseConfig.class})
@Slf4j
@Transactional(transactionManager = "tenantTransactionManager")
public class CompanyRepositoryTest {

  @PersistenceContext(unitName = TenantDatabaseConfig.PERSISTENCE_UNIT_NAME)
  EntityManager entityManager;

  @Autowired
  TransactionManager transactionManager;

  @Autowired
  CompanyRepository companyRepository;

  @Test
  @Sql(scripts = {"/tenant.sql"})
  void test() {
    companyRepository.findAll();
    entityManager.clear();
  }
}
