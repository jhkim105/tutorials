package jhkim105.tutorials.spring.cloud.stream.rabbitmq;

import java.util.function.Consumer;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class SpringCloudStreamRabbitmqApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamRabbitmqApplication.class, args);
	}

	@Bean
	public Function<String, String> uppercase() {
		return value -> {
			log.info("Received(uppercase): {}", value );
			return value.toUpperCase();
		};
	}


	@Bean
	public Consumer<String> print() {
		return s -> log.info("Received(print): {}", s);
	}

}
