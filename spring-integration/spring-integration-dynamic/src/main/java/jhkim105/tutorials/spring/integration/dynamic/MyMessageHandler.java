package jhkim105.tutorials.spring.integration.dynamic;

import java.io.File;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import jhkim105.tutorials.spring.integration.dynamic.data.MessageLog;
import jhkim105.tutorials.spring.integration.dynamic.data.MessageLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyMessageHandler {

  private final MessageLogRepository messageLogRepository;
  private final RabbitTemplate rabbitTemplate;
  private final AppProperties appProperties;

  public void handleMaster(MessageDto dto) {
    log.info("handleMaster->{}", dto);
    writeResult("result.txt", dto);
    int queueNumber = Math.abs(dto.getId().hashCode()) % appProperties.getQueueCount();
    String queueName = String.format("%s_%s", IntegrationConfig.QUEUE_NAME, queueNumber);
    rabbitTemplate.convertAndSend(queueName, dto);
  }

  @ServiceActivator
  public void handle(MessageDto dto) {
    log.info("handle->{}", dto);
    int queueNumber = Math.abs(dto.getId().hashCode()) % appProperties.getQueueCount();
    save(dto, queueNumber);
  }

  private void save(MessageDto dto, int queueNumber) {
    MessageLog message = MessageLog.builder()
        .messageId(dto.getId())
        .messageCreatedDate(dto.getCreatedAt())
        .queue(queueNumber)
        .build();
    messageLogRepository.save(message);
  }

  @SneakyThrows
  @PostConstruct
  public void initializeFile() {
    FileUtils.write(new File(getResultFilePath("result.txt")), "", StandardCharsets.UTF_8, false);
  }


  @Synchronized
  public void writeResult(String fileName, MessageDto dto) {
    String filePath = getResultFilePath(fileName);
    try {
      FileUtils.write(new File(filePath), dto.toString(), StandardCharsets.UTF_8, true);
      FileUtils.write(new File(filePath), System.lineSeparator(), StandardCharsets.UTF_8, true);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private String getResultFilePath(String fileName) {
    return String.format("%s/%s", "/Users/jihwankim/dev/my/tutorials/spring-integration/spring-integration-dynamic", fileName);
  }

}
