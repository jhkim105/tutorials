package jhkim105.tutorials.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
public class KafkaTopicConfig {

  @Bean
  public DefaultErrorHandler defaultErrorHandler() {
    return new DefaultErrorHandler(new FixedBackOff(0, 2));
  }

  @Bean
  public NewTopic topicSample() {
    return TopicBuilder.name(Topics.SAMPLE)
        .partitions(1)
        .replicas(1)
//        .compact() // cleanup.policy=compact
        .build();
  }
  public static class Topics {
    public static final String SAMPLE = "queue.sample";
  }
}
