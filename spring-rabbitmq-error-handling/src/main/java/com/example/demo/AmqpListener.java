package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AmqpListener {

  private static final Integer MAX_RETRY_COUNT = 3;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = AmqpConfig.QUEUE_MESSAGES, concurrency = "1")
  public void receiveMessage(Message message) {
    log.debug("received: {}", new String(message.getBody()));
//    throw new RuntimeException("intended error");
    throw new AmqpRejectAndDontRequeueException("intended error");
  }

  @RabbitListener(queues = AmqpConfig.QUEUE_MESSAGES_DLQ)
  public void processFailedMessages(Message message) {
    log.debug("Received failed message: {}, receivedRoutingKey:{}",
        new String(message.getBody()),
        message.getMessageProperties().getReceivedRoutingKey());

    Integer retryCount =  message.getMessageProperties().getHeader(AmqpConfig.HEADER_X_RETRIES_COUNT);
    if (retryCount == null)
      retryCount = 1;

    log.debug("retry count:{}", retryCount);
    if (retryCount > MAX_RETRY_COUNT) {
      log.debug("Retry count(max:{}, count:{}) over. discarding message.", MAX_RETRY_COUNT, retryCount);
      return;
    }

    message.getMessageProperties().getHeaders().put(AmqpConfig.HEADER_X_RETRIES_COUNT, ++retryCount);
    rabbitTemplate.send(AmqpConfig.EXCHANGE_MESSAGES, message.getMessageProperties().getReceivedRoutingKey(), message);
  }

}
