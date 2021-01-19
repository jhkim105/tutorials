package com.example.springintegrationdynamic;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.ClientParameters;
import com.rabbitmq.http.client.domain.QueueInfo;
import java.util.List;
import org.junit.jupiter.api.Test;

public class RabbitClientTest {

  @Test
  void delete() throws Exception {
    String apiUrl = "http://localhost:15672/api";
    String username = "guest";
    String password = "guest";
    Client client = new Client(new ClientParameters().url(apiUrl).username(username).password(password));
    List<QueueInfo> queueInfoList = client.getQueues();
    queueInfoList.forEach( qi -> client.deleteQueue("/", qi.getName()));
  }
}
