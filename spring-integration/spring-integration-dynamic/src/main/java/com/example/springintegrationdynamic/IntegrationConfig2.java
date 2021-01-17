package com.example.springintegrationdynamic;

import java.util.stream.IntStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

//@Configuration
@RequiredArgsConstructor
public class IntegrationConfig2 {

  public final static String QUEUE_NAME = "dynamic2";
  private final MyMessageHandler myMessageHandler;
  private final ConnectionFactory connectionFactory;
  private final IntegrationFlowContext integrationFlowContext;

  @Value("${queue.count}")
  private int flowCount = 5;

  @Bean
  public Declarables queues2() {
    Declarables declarables = new Declarables();
    IntStream.range(0, flowCount).forEach( i -> declarables.getDeclarables().add(new Queue(subQueueName(i))));
    return declarables;
  }



  private String subQueueName(int i) {
    return String.format("%s_%s", QUEUE_NAME, i);
  }

  @PostConstruct
  public void createFlow() {
    IntStream.range(0, flowCount).forEach( i -> integrationFlowContext.registration(subFlow(connectionFactory, i)).id(subQueueName(i)).register());
  }

  private IntegrationFlow subFlow(ConnectionFactory connectionFactory, int i) {
    String queueName = subQueueName(i);
    return IntegrationFlows.from(
        Amqp.inboundAdapter(simpleMessageListenerContainer(connectionFactory, queueName, 1))
      ).handle(myMessageHandler, "handle" + i).get();
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
