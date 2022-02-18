package jhkim105.tutorials.spring.cloud.stream.rabbitmq;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest
@Import({TestChannelBinderConfiguration.class})
class SpringCloudStreamRabbitmqApplicationTests {

	@Autowired
	private InputDestination input;

	@Autowired
	private OutputDestination output;

	@Test
	void test() {
		input.send(new GenericMessage<>("hello".getBytes()));
		Assertions.assertThat(output.receive().getPayload()).isEqualTo("HELLO".getBytes());
	}

}
