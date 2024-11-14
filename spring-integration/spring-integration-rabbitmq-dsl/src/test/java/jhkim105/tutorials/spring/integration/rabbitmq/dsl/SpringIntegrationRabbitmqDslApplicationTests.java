package jhkim105.tutorials.spring.integration.rabbitmq.dsl;

import jhkim105.tutorials.spring.integration.rabbitmq.dsl.OutboundConfig.OutboundGateway;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringIntegrationRabbitmqDslApplicationTests {

  @Autowired(required = false)
  private OutboundGateway outboundGateway;

  @Test
  void send() {
    outboundGateway.send("a");
    outboundGateway.send("b");
  }

}
