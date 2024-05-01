package jhkim105.tutorials.spring.data.multiple.datasource.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
