package jhkim105.tutorials.spring.integration.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.inbound.AmqpInboundGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
@Slf4j
public class InboundConfig {

  @Bean
  public MessageChannel amqpInputChannel() {
    return new DirectChannel();
  }

  @Bean
  public AmqpInboundGateway inbound(SimpleMessageListenerContainer listenerContainer,
      @Qualifier("amqpInputChannel") MessageChannel channel) {
    AmqpInboundGateway gateway = new AmqpInboundGateway(listenerContainer);
    gateway.setRequestChannel(channel);
    gateway.setDefaultReplyTo("bar");
    return gateway;
  }

  @Bean
  public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
    SimpleMessageListenerContainer container =
        new SimpleMessageListenerContainer(connectionFactory);
    container.setQueueNames("foo");
    container.setConcurrentConsumers(2);
    container.setDefaultRequeueRejected(false);
    // ...
    return container;
  }

  @Bean
  @ServiceActivator(inputChannel = "amqpInputChannel")
  public MessageHandler handler() {
    return new AbstractReplyProducingMessageHandler() {

      @Override
      protected Object handleRequestMessage(Message<?> requestMessage) {
        log.debug("message:{}", requestMessage.getPayload());
        return "reply to " + requestMessage.getPayload();
      }

    };
  }
}
