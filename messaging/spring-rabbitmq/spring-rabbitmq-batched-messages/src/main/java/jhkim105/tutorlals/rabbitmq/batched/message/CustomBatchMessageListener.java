package jhkim105.tutorlals.rabbitmq.batched.message;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.BatchMessageListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomBatchMessageListener implements BatchMessageListener {

  @Override
  public void onMessageBatch(List<Message> messages) {
    List<String> payloadList = messages.stream().map(Message::getBody).map(String::new).collect(Collectors.toList());
    log.info("onMessageBatch:{}", payloadList);
  }
}
