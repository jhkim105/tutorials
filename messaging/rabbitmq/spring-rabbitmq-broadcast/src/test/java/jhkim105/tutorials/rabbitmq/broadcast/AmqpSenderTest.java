package jhkim105.tutorials.rabbitmq.broadcast;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AmqpSenderTest {

  @Autowired
  AmqpSender amqpSender;

  @Test
  public void send() {
    amqpSender.sendTopicMessage("very.important.message", "topic broadcast");
    amqpSender.sendTopicMessage("very.important.error", "topic broadcast");
    amqpSender.sendFanoutMessage("fanout broadcast");
  }
}