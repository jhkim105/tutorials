package com.example.springintegrationdynamic;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
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
	void test() {
		myMessageHandler.initializeFile();
		List<String> list = Arrays.asList("id01", "id02", "id03", "id04", "id05");
		IntStream.range(0, 10000).parallel().forEach( i -> {
			Collections.shuffle(list);
			rabbitTemplate.convertAndSend(IntegrationConfig.QUEUE_NAME, new MessageDto(list.get(0)));
			rabbitTemplate.convertAndSend(IntegrationConfig.QUEUE_NAME+ "_0", new MessageDto(list.get(0)));
		});
	}
}
