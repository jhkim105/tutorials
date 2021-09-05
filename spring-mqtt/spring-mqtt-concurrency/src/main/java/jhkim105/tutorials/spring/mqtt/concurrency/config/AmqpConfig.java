package jhkim105.tutorials.spring.mqtt.concurrency.config;

import jhkim105.tutorials.spring.mqtt.concurrency.service.ConferenceSttLogSaveActivator3;
import jhkim105.tutorials.spring.mqtt.concurrency.service.SttLogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.json.JsonToObjectTransformer;

@Slf4j
@Configuration
public class AmqpConfig {

  public static final String CONFERENCE_STT_LOG_SAVE_FLOW = "conferenceSttLogSaveFlow";


  @Autowired
  private ConferenceSttLogSaveActivator3 conferenceSttLogSaveActivator;


  @Bean
  public Queue mqttQueue() {
    return new Queue(CONFERENCE_STT_LOG_SAVE_FLOW);
  }

  @Bean
  public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory) {
    return IntegrationFlows.from(Amqp.inboundAdapter(simpleMessageListenerContainer(connectionFactory, CONFERENCE_STT_LOG_SAVE_FLOW, 1)))
        .transform(new JsonToObjectTransformer(SttLogMessage.class))
        .handle(conferenceSttLogSaveActivator)
        .get();
  }

  private SimpleMessageListenerContainer simpleMessageListenerContainer(ConnectionFactory connectionFactory, String queueName, int consumerCount) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setDefaultRequeueRejected(false);
    container.setConcurrentConsumers(consumerCount);
    return container;
  }
}
