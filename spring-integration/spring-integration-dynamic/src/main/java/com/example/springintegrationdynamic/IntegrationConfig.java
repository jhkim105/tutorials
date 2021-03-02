package com.example.springintegrationdynamic;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.context.IntegrationFlowContext;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

  public final static String QUEUE_NAME = "dynamic";
  private final IntegrationFlowContext integrationFlowContext;
  private final ConnectionFactory connectionFactory;
  private final MyMessageHandler myMessageHandler;
  private final AmqpUtils amqpUtils;
  @Value("${queue.count}")
  private int queueCount;

  @Bean
  public Declarables queues() {
    Declarables declarables = new Declarables();
    declarables.getDeclarables().add(new Queue(QUEUE_NAME));
    return declarables;
  }

  @Bean
  public IntegrationFlow mainFlow(ConnectionFactory connectionFactory) {
    return IntegrationFlows
        .from(
            Amqp.inboundAdapter(connectionFactory, QUEUE_NAME)
                .configureContainer(
                    config -> config.concurrentConsumers(5)
                        .defaultRequeueRejected(false)
                        .prefetchCount(1))
        )
        .handle(myMessageHandler)
        .get();
  }

  private String subQueueName(int i) {
    return String.format("%s_%s", QUEUE_NAME, i);
  }

  @PostConstruct
  public void createFlow() {
    IntStream.range(0, queueCount).forEach(i -> amqpUtils.declareQueue(subQueueName(i), 1));
//    IntStream.range(0, queueCount).forEach( i -> integrationFlowContext.registration(subFlow(i)).id(subQueueName(i)).register());
    IntStream.range(0, queueCount).forEach( i -> integrationFlowContext.registration(subFlow2(i)).id(subQueueName(i)).register());
  }

  private IntegrationFlow subFlow(int i) {
    String queueName = subQueueName(i);
    return IntegrationFlows.from(
        Amqp.inboundAdapter(simpleMessageListenerContainer(queueName, 1, 1))
    ).handle(myMessageHandler, "handle" + i).get();
  }

  private IntegrationFlow subFlow2(int i) {
    String queueName = subQueueName(i);
    return IntegrationFlows.from(
        Amqp.inboundAdapter(connectionFactory,  queueName)
        .configureContainer(
            cfg -> cfg.concurrentConsumers(1)
                .defaultRequeueRejected(false)
                .prefetchCount(1))
      ).handle(myMessageHandler, "handle").get();
  }

  private SimpleMessageListenerContainer simpleMessageListenerContainer(String queueName, int consumerCount, int fetchCount) {
    SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setQueueNames(queueName);
    container.setDefaultRequeueRejected(false);
    container.setConcurrentConsumers(consumerCount);
    container.setPrefetchCount(fetchCount);
    return container;
  }

}
