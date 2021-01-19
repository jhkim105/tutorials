package com.example.springintegrationdynamic;

import com.example.springintegrationdynamic.data.MessageLog;
import com.example.springintegrationdynamic.data.MessageLogRepository;
import java.io.File;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMessageHandler {

  @Autowired
  private MessageLogRepository messageLogRepository;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${queue.count}")
  private int queueCount;

  @ServiceActivator
  public void handle(MessageDto dto) {
    log.info("handle->{}", dto);
    int queueNumber = Math.abs(dto.hashCode()) % queueCount;
    String queueName = String.format("%s_%s", IntegrationConfig.QUEUE_NAME, queueNumber);
    rabbitTemplate.convertAndSend(queueName, dto);
  }

  public void handle0(MessageDto dto) {
    log.info("handle0->{}", dto);
    save(dto, 0);
    writeResult("result_0.txt", dto);
  }


  public void handle1(MessageDto dto) {
    log.info("handle1->{}", dto);
    save(dto, 1);
    writeResult("result_1.txt", dto);
  }


  public void handle2(MessageDto dto) {
    log.info("handle2->{}", dto);
    save(dto, 2);
    writeResult("result_2.txt", dto);
  }

  private void save(MessageDto dto, int queueNumber) {
    MessageLog message = MessageLog.builder()
        .messageId(dto.getId())
        .messageCreatedDate(dto.getCreatedAt())
        .build();
    messageLogRepository.save(message);
  }

  @SneakyThrows
  public void initializeFile() {
    FileUtils.write(new File(getResultFilePath("result.txt")), "", StandardCharsets.UTF_8, false);
    FileUtils.write(new File(getResultFilePath("result_0.txt")), "", StandardCharsets.UTF_8, false);
    FileUtils.write(new File(getResultFilePath("result_1.txt")), "", StandardCharsets.UTF_8, false);
    FileUtils.write(new File(getResultFilePath("result_2.txt")), "", StandardCharsets.UTF_8, false);
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
