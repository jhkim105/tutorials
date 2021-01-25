package com.example.demo;

import com.rabbitmq.http.client.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitClientUtils {

  private final Client rabbitClient;

  public long getUnackedCount() {
    return rabbitClient.getOverview().getQueueTotals().getMessagesUnacknowledged();
  }

  public int getQueueCount() {
    return rabbitClient.getQueues().size();
  }
}
