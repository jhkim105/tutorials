package jhkim105.tutorials.spring.data.jpa;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jhkim105.tutorials.spring.data.jpa.service.UserManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	UserManager userManager;

	@Test
	void test() {
		log.debug("{}", entityManager);
		log.debug("{}", ((EntityManagerFactoryInfo)entityManager.getEntityManagerFactory()).getPersistenceUnitName());
	}

	@Test
	void testUserManager() {
		assertThrows(EntityNotFoundException.class, () -> userManager.get("non-exists-user"));
	}
}
