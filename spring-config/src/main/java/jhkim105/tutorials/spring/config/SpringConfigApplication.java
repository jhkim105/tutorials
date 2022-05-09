package jhkim105.tutorials.spring.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class SpringConfigApplication implements ApplicationRunner {

	private final AppProperties appProperties;

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("{}", appProperties);
	}
}
