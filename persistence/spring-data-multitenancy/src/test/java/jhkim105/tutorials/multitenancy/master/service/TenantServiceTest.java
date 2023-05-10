package jhkim105.tutorials.multitenancy.master.service;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Disabled
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TenantServiceTest {

  @Autowired
  TenantService tenantService;

  @Autowired
  TenantRepository tenantRepository;



  @Test
  @Order(1)
  void create() {
    tenantService.createTenant("user1");
  }

  @Test
  @Order(2)
  void delete() {
    Tenant tenant = tenantRepository.findByName("user1");
    tenantService.deleteTenant(tenant);
  }

  @Test
  @Order(3)
  void dropOrphanDatabases() {
    tenantService.dropOrphanDatabases();
  }

}