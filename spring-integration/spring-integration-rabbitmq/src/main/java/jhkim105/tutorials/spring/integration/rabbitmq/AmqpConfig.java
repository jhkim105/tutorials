package jhkim105.tutorials.spring.integration.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

  @Bean
  public Queue foo() {
    return new Queue("foo");
  }

}
