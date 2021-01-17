package com.example.springintegrationdynamic;

import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
  @Value("${queue.count}")
  private int queueCount;

  @Bean
  public Declarables queues() {
    Declarables declarables = new Declarables();
    declarables.getDeclarables().add(new Queue(QUEUE_NAME));
    IntStream.range(0, queueCount).forEach( i -> declarables.getDeclarables().add(new Queue(subQueueName(i))));
    return declarables;
  }

  @Bean
  public IntegrationFlow flow(ConnectionFactory connectionFactory) {
    return IntegrationFlows
        .from(Amqp.inboundAdapter(connectionFactory, QUEUE_NAME)
                  .configureContainer(config -> config.concurrentConsumers(5)
                .defaultRequeueRejected(false)
                .prefetchCount(1))
        )
        .handle(myMessageHandler)
        .split()
        .<MessageDto, Integer>route(dto -> Math.abs(dto.hashCode() % queueCount),
            m -> {
              for(int i = 0; i < queueCount; i++) {
                m.subFlowMapping(i, subFlow(connectionFactory, i));
              }
            })
        .get();
  }

  private String subQueueName(int i) {
    return String.format("%s_%s", QUEUE_NAME, i);
  }

  private IntegrationFlow subFlow(ConnectionFactory connectionFactory, int i) {
    String queueName = subQueueName(i);
    return IntegrationFlows.from(
          Amqp.inboundAdapter(connectionFactory, queueName)
              .configureContainer(config -> config.concurrentConsumers(1)
              .defaultRequeueRejected(false)
              .prefetchCount(1))
      ).handle(myMessageHandler, "handle" + i).get();

  }


}
