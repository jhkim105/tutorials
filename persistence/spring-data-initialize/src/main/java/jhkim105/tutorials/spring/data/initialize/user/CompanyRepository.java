package jhkim105.tutorials.spring.data.initialize.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {

}
