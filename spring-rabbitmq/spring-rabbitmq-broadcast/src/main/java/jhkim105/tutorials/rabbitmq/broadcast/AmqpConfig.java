package jhkim105.tutorials.rabbitmq.broadcast;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  public static final String TOPIC_QUEUE_A = "topic-queue-a";
  public static final String TOPIC_QUEUE_B = "topic-queue-b";
  public static final String FANOUT_QUEUE_A = "fanout-queue-a";
  public static final String FANOUT_QUEUE_B = "fanout-queue-b";
  public static final String TOPIC_EXCHANGE = "topic-exchange";
  public static final String FANOUT_EXCHANGE = "fanout-exchange";

  @Bean
  public Declarables topicBindings() {
    Queue topicQueue1 = new Queue(TOPIC_QUEUE_A, false);
    Queue topicQueue2 = new Queue(TOPIC_QUEUE_B, false);

    TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE);

    return new Declarables(
        topicQueue1,
        topicQueue2,
        topicExchange,
        BindingBuilder
            .bind(topicQueue1)
            .to(topicExchange).with("*.important.*"),
        BindingBuilder
            .bind(topicQueue2)
            .to(topicExchange).with("#.error"));
  }


  @Bean
  public Declarables fanoutBindings() {
    Queue fanoutQueue1 = new Queue(FANOUT_QUEUE_A, false);
    Queue fanoutQueue2 = new Queue(FANOUT_QUEUE_B, false);

    FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE);

    return new Declarables(
        fanoutQueue1,
        fanoutQueue2,
        fanoutExchange,
        BindingBuilder
            .bind(fanoutQueue1)
            .to(fanoutExchange),
        BindingBuilder
            .bind(fanoutQueue2)
            .to(fanoutExchange));
  }


}
