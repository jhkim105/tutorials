package jhkim105.tutorials.spring.integration.dynamic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringIntegrationDynamicApplicationTests {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	MyMessageHandler myMessageHandler;

	@Test
	@Disabled
	void test() {
		myMessageHandler.initializeFile();
		List<String> list = Arrays.asList("id01", "id02", "id03", "id04", "id05");
		IntStream.range(0, 1000).parallel().forEach( i -> {
			Collections.shuffle(list);
			rabbitTemplate.convertAndSend(IntegrationConfig.QUEUE_NAME, new MessageDto(list.get(0)));
		});
	}
}
