package jhkim105.tutorials.multitenancy.master.repository;

import jhkim105.tutorials.multitenancy.master.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

  Tenant findFirstBy();

  Tenant findByName(String name);

  boolean existsByName(String tenantName);
  boolean existsByDbName(String dbName);
}
