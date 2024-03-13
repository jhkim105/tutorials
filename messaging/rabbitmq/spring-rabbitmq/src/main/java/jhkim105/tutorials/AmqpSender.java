package jhkim105.tutorials;

import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AmqpSender {

  private final RabbitTemplate rabbitTemplate;

  public void send(String queueName, final Serializable message) {
    rabbitTemplate.convertAndSend(queueName, message);
  }
}
