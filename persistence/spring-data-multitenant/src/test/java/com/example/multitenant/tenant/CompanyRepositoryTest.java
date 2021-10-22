package com.example.multitenant.tenant;

import com.example.multitenant.config.MasterDatabaseConfig;
import com.example.multitenant.config.TenantDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({MasterDatabaseConfig.class, TenantDatabaseConfig.class})
@Slf4j
public class CompanyRepositoryTest {

  @Autowired
  CompanyRepository companyRepository;

  @Test
  void test() {
    companyRepository.findAll();
  }
}
