package jhkim105.tutorials.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
public class KafkaConfig {

  @Bean
  public DefaultErrorHandler defaultErrorHandler() {
    return new DefaultErrorHandler(new FixedBackOff(0, 2));
  }


}
