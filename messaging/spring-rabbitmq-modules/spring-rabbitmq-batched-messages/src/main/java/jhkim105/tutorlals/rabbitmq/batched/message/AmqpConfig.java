package jhkim105.tutorlals.rabbitmq.batched.message;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class AmqpConfig {

  static final String QUEUE_A = "queue-a";

  @Bean
  Queue queue() {
    return new Queue(QUEUE_A, false);
  }

  @Bean
  public SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(QUEUE_A);
    container.setMessageListener(new CustomBatchMessageListener());
    container.setBatchSize(10);
    container.setConsumerBatchEnabled(true);
//    container.setDeBatchingEnabled(true);
    return container;
  }


  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setBatchListener(true);
    factory.setConsumerBatchEnabled(true);
    factory.setBatchSize(10);
    return factory;
  }

  @RabbitListener(queues = QUEUE_A)
  public void listen(List<String> list) {
    log.info("messages->{}", list);
  }


}
