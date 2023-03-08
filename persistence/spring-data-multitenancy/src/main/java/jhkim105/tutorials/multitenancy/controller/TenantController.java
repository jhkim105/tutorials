package jhkim105.tutorials.multitenancy.controller;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import jhkim105.tutorials.multitenancy.master.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tenants")
public class TenantController {

  private final TenantService tenantService;

  @GetMapping
  public ResponseEntity<List<Tenant>> getAll() {
    List<Tenant> tenants = tenantService.findAll();
    return ResponseEntity.ok(tenants);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id) {
    Tenant tenant = tenantService.findById(id);
    if (tenant == null) {
      throw new EntityNotFoundException(String.format("Entity not found. id:[%s]", id));
    }
    tenantService.deleteTenant(tenant);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/drop-database")
  public ResponseEntity<Void> dropDatabase() {
    tenantService.dropOrphanDatabases();
    return ResponseEntity.ok().build();
  }

}
