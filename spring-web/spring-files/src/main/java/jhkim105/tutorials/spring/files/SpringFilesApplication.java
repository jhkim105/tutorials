package jhkim105.tutorials.spring.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringFilesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFilesApplication.class, args);
	}

}
