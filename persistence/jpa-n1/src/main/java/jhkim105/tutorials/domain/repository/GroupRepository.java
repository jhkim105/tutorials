package jhkim105.tutorials.domain.repository;

import jhkim105.tutorials.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {

}
