package jhkim105.tutorials.multitenancy.master.repository;

import jhkim105.tutorials.multitenancy.master.MasterDatabaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(MasterDatabaseConfig.class)
@Slf4j
class TenantRepositoryTest {

  @Autowired
  TenantRepository tenantRepository;


  @Test
  void findFirst() {
    tenantRepository.findFirstBy();
  }

}