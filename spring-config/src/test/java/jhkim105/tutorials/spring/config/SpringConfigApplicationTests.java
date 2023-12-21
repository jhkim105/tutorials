package jhkim105.tutorials.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringConfigApplicationTests {

	@Autowired
	private AppProperties appProperties;

	@Test
	void contextLoads() {
		log.info("{}", appProperties);
	}

}
