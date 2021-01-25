package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmqpListener {


  @RabbitListener(queues = AmqpConfig.TOPIC_QUEUE_A, concurrency = "1")
  public void topicQueueA(Message message) {
    log.debug("topicQueueA: {}", new String(message.getBody()));
  }

  @RabbitListener(queues = AmqpConfig.TOPIC_QUEUE_B, concurrency = "1")
  public void topicQueueB(Message message) {
    log.debug("topicQueueB: {}", new String(message.getBody()));
  }

  @RabbitListener(queues = AmqpConfig.FANOUT_QUEUE_A, concurrency = "1")
  public void fanoutQueueA(Message message) {
    log.debug("fanoutQueueA: {}", new String(message.getBody()));
  }

  @RabbitListener(queues = AmqpConfig.FANOUT_QUEUE_B, concurrency = "1")
  public void fanoutQueueB(Message message) {
    log.debug("fanoutQueueB: {}", new String(message.getBody()));
  }

}
