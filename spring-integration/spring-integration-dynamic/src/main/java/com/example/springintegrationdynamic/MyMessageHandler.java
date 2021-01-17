package com.example.springintegrationdynamic;

import com.example.springintegrationdynamic.data.MessageLog;
import com.example.springintegrationdynamic.data.MessageLogRepository;
import java.io.File;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyMessageHandler {

  @Autowired
  private MessageLogRepository messageLogRepository;

  @ServiceActivator
  public MessageDto handle(MessageDto dto) {
    log.info("handle->{}", dto);
    writeResult("result.txt", dto);
    save(dto);
    return dto;
  }

  private void save(MessageDto dto) {
    MessageLog message = MessageLog.builder()
        .messageId(dto.getId())
        .messageCreatedDate(dto.getCreatedAt())
        .build();
    messageLogRepository.save(message);
  }

  public void handle0(MessageDto dto) {
    log.info("handle0->{}", dto);
    writeResult("result_0.txt", dto);
  }


  public void handle1(MessageDto dto) {
    log.info("handle1->{}", dto);
    writeResult("result_1.txt", dto);
  }


  public void handle2(MessageDto dto) {
    log.info("handle2->{}", dto);
    writeResult("result_2.txt", dto);
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
