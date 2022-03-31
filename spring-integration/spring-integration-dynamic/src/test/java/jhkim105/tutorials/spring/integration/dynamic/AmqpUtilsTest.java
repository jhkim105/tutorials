package jhkim105.tutorials.spring.integration.dynamic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpUtilsTest {

  @Autowired
  AmqpUtils amqpUtils;

  @Test
  @Disabled
  void deleteQueue() {
    amqpUtils.deleteQueues();
  }


}