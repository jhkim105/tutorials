package com.example.springintegrationdynamic;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmqpUtils {

  @Autowired
  private AmqpAdmin amqpAdmin;

  @Autowired
  private Client rabbitmqClient;

  public void declareQueue(String queueName, int maxInactiveMinutes) {
    Queue queue = new Queue(queueName, true, false, false, getQueueExpiresArgument(maxInactiveMinutes));
    amqpAdmin.declareQueue(queue);
  }

  private Map<String, Object> getQueueExpiresArgument(int maxInactiveMinutes) {
    Map<String, Object> args = new HashMap<>();
    args.put("x-expires", 60 * maxInactiveMinutes * 1000);
    return args;
  }

  public void deleteQueue(String queueName) {
    amqpAdmin.deleteQueue(queueName);
  }

  public boolean exists(String queueName) {
    boolean exists = amqpAdmin.getQueueInfo(queueName) != null;
    return exists;
  }

  public boolean existsConsumer(String queueName) {
    Properties properties = amqpAdmin.getQueueProperties(queueName);
    if (properties == null) {
      return false;
    }
    int consumerCount = (Integer)properties.get("QUEUE_CONSUMER_COUNT");
    return consumerCount > 0;
  }

  public Queue getQueue(String virtualHost, String queueName) {
    QueueInfo qi = rabbitmqClient.getQueue(virtualHost, queueName);
    return qi == null ? null : new Queue(qi.getName(), qi.isDurable(), qi.isExclusive(), qi.isAutoDelete(), qi.getArguments());
  }

  public void deleteQueues() {
    List<QueueInfo> queueInfoList = rabbitmqClient.getQueues();
    queueInfoList.forEach( qi -> deleteQueue(qi.getName()));
  }

}
