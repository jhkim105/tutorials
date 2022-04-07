package jhkim105.tutorials.rabbitmq.monitoring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RabbitClientUtilsTest {

  @Autowired
  RabbitClientUtils rabbitClientUtils;

  @Test
  void messageUnackedCount() {
    log.info("unackedCount:{}", rabbitClientUtils.getMessageUnackedCount());
  }

  @Test
  void messageReadyCount() {
    log.info("unackedCount:{}", rabbitClientUtils.getMessageReadyCount());
  }

  @Test
  void queueCount() {
    log.info("queueCount:{}", rabbitClientUtils.getQueueCount());
  }
}
