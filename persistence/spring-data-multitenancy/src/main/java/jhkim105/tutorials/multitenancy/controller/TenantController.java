package jhkim105.tutorials.multitenancy.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.repository.TenantRepository;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import jhkim105.tutorials.multitenancy.tenant.TenantDatabaseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenants")
public class TenantController {

  private final TenantService tenantService;
  private final TenantRepository tenantRepository;
  private final TenantDatabaseHelper tenantDatabaseHelper;

  @GetMapping
  public ResponseEntity<List<Tenant>> getAll() {
    List<Tenant> tenants = tenantRepository.findAll();
    return ResponseEntity.ok(tenants);
  }

  @PostMapping("/create")
  public ResponseEntity<Tenant> create(String name) {
    Tenant tenant =  tenantService.createTenant(name);
    return ResponseEntity.ok(tenant);
  }


  @PostMapping("/delete")
  public ResponseEntity<?> delete(String name) {
    Tenant tenant = tenantRepository.findByName(name);
    if (tenant == null) {
      throw new EntityNotFoundException(String.format("Entity not found. name:[%s]", name));
    }
    tenantService.deleteTenant(tenant);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/drop")
  public ResponseEntity<?> drop(String name) {
    Tenant tenant = tenantRepository.findByName(name);
    if (tenant == null) {
      throw new EntityNotFoundException(String.format("Entity not found. name:[%s]", name));
    }
    tenantDatabaseHelper.dropDatabase(tenant);
    return ResponseEntity.ok().build();
  }
}
