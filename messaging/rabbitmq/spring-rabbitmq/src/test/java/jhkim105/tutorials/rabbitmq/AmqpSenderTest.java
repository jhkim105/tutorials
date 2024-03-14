package jhkim105.tutorials.rabbitmq;

import static jhkim105.tutorials.rabbitmq.AmqpConfig.QUEUE_SAMPLE;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class AmqpSenderTest {


  @Autowired
  AmqpSender amqpSender;


  @Test
  void send() {
    SampleMessage sampleMessage = new SampleMessage();
    sampleMessage.setName("Message 01");
    amqpSender.send(QUEUE_SAMPLE, sampleMessage);
  }


}