package jhkim105.tutorials.rabbitmq.monitoring;

import com.rabbitmq.http.client.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitClientUtils {

  private final Client rabbitClient;

  public int getMessageUnackedCount() {
    return (int)rabbitClient.getOverview().getQueueTotals().getMessagesUnacknowledged();
  }

  public int getMessageReadyCount() {
    return (int)rabbitClient.getOverview().getQueueTotals().getMessagesReady();
  }

  public int getQueueCount() {
    return rabbitClient.getQueues().size();
  }
}
