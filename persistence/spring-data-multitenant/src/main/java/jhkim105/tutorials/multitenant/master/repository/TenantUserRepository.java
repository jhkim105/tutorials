package jhkim105.tutorials.multitenant.master.repository;

import jhkim105.tutorials.multitenant.master.domain.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantUserRepository extends JpaRepository<TenantUser, String> {
  TenantUser findByUsername(String username);
}
