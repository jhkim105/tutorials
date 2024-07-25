package jhkim105.tutorials.config;

import jhkim105.tutorials.KafkaTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class InboundConfig {

  private final ConsumerFactory<String, String> consumerFactory;
  private final ApplicationContext applicationContext;

  @Bean
  public IntegrationFlow inboundFlow() {
    return IntegrationFlow
        .from(Kafka.messageDrivenChannelAdapter(consumerFactory, containerProperties(KafkaTopic.FOO)))
//        .from(Kafka.messageDrivenChannelAdapter(consumerFactory, KafkaTopic.FOO.getTopicName()))
        .transform(String.class, String::toUpperCase)
        .handle(this, "handle")
        .split()
        .<String, Boolean>route(s -> s.equals("A"),
            m -> m
                .subFlowMapping(true, handle1Flow())
                .subFlowMapping(false, handle2Flow())).get();
  }

  private ContainerProperties containerProperties(KafkaTopic topic) {
    var applicationName = applicationContext.getId();
    var consumerConfigGroupId = consumerFactory.getConfigurationProperties().get(ConsumerConfig.GROUP_ID_CONFIG);

    var containerProperties = new ContainerProperties(topic.getTopicName());
    if (consumerConfigGroupId == null) {
      containerProperties.setGroupId(applicationName);
    }
    return containerProperties;
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
