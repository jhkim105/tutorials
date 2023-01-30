package jhkim105.tutorials.spring_security_role.user.repository;

import jhkim105.tutorials.spring_security_role.user.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {

  Role findByName(String name);
}
