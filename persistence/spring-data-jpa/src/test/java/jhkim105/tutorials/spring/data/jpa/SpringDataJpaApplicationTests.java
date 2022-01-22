package jhkim105.tutorials.spring.data.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;

@SpringBootTest
@Slf4j
class SpringDataJpaApplicationTests {

	@Test
	void contextLoads() {
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	void test() {
		log.debug("{}", entityManager);
		log.debug("{}", ((EntityManagerFactoryInfo)entityManager.getEntityManagerFactory()).getPersistenceUnitName());
	}
}
