package jhkim105.tutorials.config;

import jhkim105.tutorials.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InboundConfig {

  private final ConsumerFactory<String, String> consumerFactory;
  private final ConcurrentKafkaListenerContainerFactory<String, String> containerFactory;

  @Bean
  public IntegrationFlow inboundFlow() {
    return IntegrationFlow
//        .from(Kafka.messageDrivenChannelAdapter(consumerFactory, KafkaTopic.FOO.getTopicName()))
        .from(Kafka.messageDrivenChannelAdapter(containerFactory.createContainer(KafkaTopic.FOO.getTopicName())))
        .transform(String.class, String::toUpperCase)
        .handle(this, "handle")
        .split()
        .<String, Boolean>route(s -> s.equals("A"),
            m -> m
                .subFlowMapping(true, handle1Flow())
                .subFlowMapping(false, handle2Flow())).get();
  }


  @Bean
  public IntegrationFlow handle1Flow() {
    return f -> f.handle(m -> log.debug("handle1"));
  }

  @Bean
  public IntegrationFlow handle2Flow() {
    return f -> f.handle(m -> log.debug("handle2"));
  }

  public String handle(String message) {
    log.debug("message:{}", message);
    return message;
  }


  public String handleAndReply(String message) {
    log.debug("message:{}", message);
    return String.format("reply to %s", message);
  }


}
