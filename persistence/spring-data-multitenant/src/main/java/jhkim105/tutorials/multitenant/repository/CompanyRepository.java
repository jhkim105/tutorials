package jhkim105.tutorials.multitenant.repository;

import jhkim105.tutorials.multitenant.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String>, CompanyRepositoryCustom{

  Company getByName(String name);


}
