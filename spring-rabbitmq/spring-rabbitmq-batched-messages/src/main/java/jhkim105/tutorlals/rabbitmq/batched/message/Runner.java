package jhkim105.tutorlals.rabbitmq.batched.message;

import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Runner implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;

  private final MessageListenerContainer messageListenerContainer;

  @Override
  public void run(String... args) throws Exception {
    log.info("messageListenerContainer -> {}", messageListenerContainer);
    IntStream.rangeClosed(1, 23).parallel().forEach(i -> rabbitTemplate.convertAndSend(AmqpConfig.QUEUE_A, "" + i));
  }
}
