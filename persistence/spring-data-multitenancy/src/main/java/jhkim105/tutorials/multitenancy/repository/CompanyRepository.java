package jhkim105.tutorials.multitenancy.repository;

import jhkim105.tutorials.multitenancy.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String>, CompanyRepositoryCustom{

  Company getByName(String name);


}
