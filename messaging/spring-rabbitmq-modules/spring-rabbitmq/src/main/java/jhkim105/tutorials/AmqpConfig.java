package jhkim105.tutorials;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class AmqpConfig {

  static {
    System.setProperty("spring.amqp.deserialization.trust.all", "true");
  }

  public static final String QUEUE_SAMPLE = "sample-queue";

  @Bean
  public Queue sampleQueue() {
    return new Queue(QUEUE_SAMPLE);
  }


//  @Bean
//  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//    RabbitTemplate template = new RabbitTemplate(connectionFactory);
//    template.setMessageConverter(simpleMessageConverter());
//    return template;
//  }
//
//  private SimpleMessageConverter simpleMessageConverter() {
//    SimpleMessageConverter simpleMessageConverter = new SimpleMessageConverter();
//    simpleMessageConverter.setAllowedListPatterns(Collections.singletonList("*"));
//    return simpleMessageConverter;
//  }

}
