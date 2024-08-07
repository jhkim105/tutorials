package jhkim105.tutorials.spring.integration.rabbitmq.dsl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OutboundConfig {

  @Bean
  public IntegrationFlow amqpOutboundFlow(AmqpTemplate amqpTemplate) {
    return IntegrationFlow.from(amqpOutboundChannel())
        .handle(Amqp.outboundAdapter(amqpTemplate)
            .routingKey("foo")) // default exchange - route to queue 'foo'
        .get();
  }

  @Bean
  public MessageChannel amqpOutboundChannel() {
    return new DirectChannel();
  }

  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel")
//  @MessagingGateway(defaultRequestChannel = "amqpOutboundChannel", defaultRequestTimeout = "50000", defaultReplyTimeout = "5000")
  public interface OutboundGateway {
    void send(String data);
  }

}
