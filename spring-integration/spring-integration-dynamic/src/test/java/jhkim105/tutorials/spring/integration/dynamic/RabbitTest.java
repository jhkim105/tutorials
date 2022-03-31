package jhkim105.tutorials.spring.integration.dynamic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class RabbitTest {

  @Autowired(required = true)
  RabbitTemplate rabbitTemplate;

  @Test
  void test() {
    log.debug("{}", rabbitTemplate);
    IntStream.range(0, 10).forEach(i -> rabbitTemplate.convertAndSend("agent", new TestData()));
  }

  @Getter
  public static class TestData implements Serializable {

    private static final long serialVersionUID = 3932144864255493201L;
    private String guid;
    private List<String> idList = Arrays.asList("agent01", "agent02", "agent03");
    public TestData() {
      Collections.shuffle(idList);
      this.guid = idList.get(0);
    }
  }
}
