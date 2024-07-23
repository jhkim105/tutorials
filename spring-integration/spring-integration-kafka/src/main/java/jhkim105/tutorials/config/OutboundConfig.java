package jhkim105.tutorials.config;

import jhkim105.tutorials.KafkaTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OutboundConfig {

  @Bean
  public IntegrationFlow amqpOutboundFlow(KafkaTemplate<String, String> template) {
    return IntegrationFlow.from(amqpOutboundChannel())
        .handle(Kafka.outboundChannelAdapter(template)
            .topic(KafkaTopic.FOO.getTopicName())) // default exchange - route to queue 'foo'
        .get();
  }

  @Bean
  public MessageChannel amqpOutboundChannel() {
    return new DirectChannel();
  }

  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
//  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel", defaultRequestTimeout = "50000", defaultReplyTimeout = "5000")
  public interface OutboundGateWay {
    void send(String data);
  }

}
