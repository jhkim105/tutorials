package jhkim105.tutorials.rabbitmq.error.handling;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpSenderTest {

  @Autowired
  AmqpSender amqpSender;

  @Test
  void send() {
    amqpSender.sendMessage("1111");
  }
}