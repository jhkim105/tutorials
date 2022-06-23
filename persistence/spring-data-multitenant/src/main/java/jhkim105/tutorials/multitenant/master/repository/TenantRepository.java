package jhkim105.tutorials.multitenant.master.repository;

import jhkim105.tutorials.multitenant.master.domain.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {

  Tenant findFirstBy();
}
