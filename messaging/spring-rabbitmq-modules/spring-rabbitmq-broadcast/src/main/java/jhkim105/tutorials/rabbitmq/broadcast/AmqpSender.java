package jhkim105.tutorials.rabbitmq.broadcast;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpSender {

  @Autowired
  private RabbitTemplate rabbitTemplate;

  public void sendTopicMessage(String routingKey, String message) {
    rabbitTemplate.convertAndSend(AmqpConfig.TOPIC_EXCHANGE, routingKey, message);
  }

  public void sendFanoutMessage(String message) {
    rabbitTemplate.convertAndSend(AmqpConfig.FANOUT_EXCHANGE, "", message);
  }

}
