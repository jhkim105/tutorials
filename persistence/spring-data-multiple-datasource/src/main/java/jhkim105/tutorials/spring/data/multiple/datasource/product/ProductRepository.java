package jhkim105.tutorials.spring.data.multiple.datasource.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}
