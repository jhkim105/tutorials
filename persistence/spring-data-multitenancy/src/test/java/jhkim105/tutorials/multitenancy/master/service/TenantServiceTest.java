package jhkim105.tutorials.multitenancy.master.service;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TenantServiceTest {

  @Autowired
  TenantService tenantService;

  @Autowired
  TenantRepository tenantRepository;


  @Test
  @Disabled
  void createAndDelete() {
    Tenant tenant = tenantService.createTenant("tenant001");

    tenantService.deleteTenant(tenant);
  }


  @Test
  @Disabled
  void create() {
    tenantService.createTenant("user1");
  }

  @Test
  @Disabled
  void delete() {
    Tenant tenant = tenantRepository.findByName("user1");
    tenantService.deleteTenant(tenant);
  }

  @Test
  void dropOrphanDatabases() {
    tenantService.dropOrphanDatabases();
  }

}