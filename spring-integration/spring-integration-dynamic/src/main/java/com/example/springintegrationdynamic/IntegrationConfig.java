package com.example.springintegrationdynamic;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

@Configuration
@RequiredArgsConstructor
public class IntegrationConfig {

  public final static String QUEUE_NAME = "dynamic";
  private final MyMessageHandler myMessageHandler;
  private final AmqpUtils amqpUtils;

  private final ConnectionFactory cachingConnectionFactory;

  @Value("${queue.count}")
  private int queueCount;

  @PostConstruct
  public void deleteGarbageQueue() {
    amqpUtils.deleteGarbageQueues();
  }

//  @Bean
//  public Declarables queues() {
//    Declarables declarables = new Declarables();
//    declarables.getDeclarables().add(new Queue(QUEUE_NAME));
//    return declarables;
//  }

  @Bean
  public Queue agentQueue() {
    return new Queue(QUEUE_NAME);
  }

  @Bean
  public IntegrationFlow masterFlow(ConnectionFactory connectionFactory) {
    return IntegrationFlows
        .from(
            Amqp.inboundAdapter(connectionFactory, QUEUE_NAME)
                .configureContainer(
                    config -> config.concurrentConsumers(5)
                        .defaultRequeueRejected(false)
                        .prefetchCount(1))
        )
        .handle(myMessageHandler, "handleMaster")
        .get();
  }

  @Bean
  public IntegrationFlow createFlow() {
    List<String> queueNames = new ArrayList<>();
    for (int i = 0; i < queueCount; i++) {
      String queueName = subQueueName(i);
      amqpUtils.declareQueue(queueName);
      queueNames.add(queueName);
    }
    return IntegrationFlows
        .from(
            Amqp.inboundAdapter(
                directMessageListenerContainer(queueNames, 1, 10))

        )
        .handle(this.myMessageHandler).get();
  }

  private String subQueueName(int i) {
    return String.format("%s_%s", QUEUE_NAME, i);
  }

  private DirectMessageListenerContainer directMessageListenerContainer(List<String> queueNames, int consumerCount, int fetchCount) {
    DirectMessageListenerContainer container = new DirectMessageListenerContainer();
    container.setConnectionFactory(cachingConnectionFactory);
    container.setQueueNames(queueNames.toArray(new String[0]));
    container.setDefaultRequeueRejected(false);
    container.setConsumersPerQueue(consumerCount);
    container.setPrefetchCount(fetchCount);
    return container;
  }


}
