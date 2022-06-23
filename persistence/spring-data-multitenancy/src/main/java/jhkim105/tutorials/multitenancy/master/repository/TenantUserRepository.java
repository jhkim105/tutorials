package jhkim105.tutorials.multitenancy.master.repository;

import jhkim105.tutorials.multitenancy.master.domain.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantUserRepository extends JpaRepository<TenantUser, String> {
  TenantUser findByUsername(String username);
}
