package com.example.demo;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConferenceSttLogSaveActivator4 {

  @Autowired
  private ConferenceSttLogRepository conferenceSttLogRepository;

  @Autowired
  private RedissonClient redissonClient;

  @ServiceActivator
  public void handle(SttLogMessage message) {
    log.debug("handle start. message->{}", message);
    SttLogMessage.Data data = message.getData();
    if (message.isNotValid()) {
      log.debug("message is not valid. message -> {}", data);
      return;
    }

    String key = String.format("%s_%s", data.getConferenceId(), data.getSeq());

    RLock lock = redissonClient.getLock(key);
    try {
      if (lock.tryLock(1, 1, TimeUnit.SECONDS)) {
        ConferenceSttLog conferenceSttLog = conferenceSttLogRepository.getByConferenceIdAndSeq(data.getConferenceId(), data.getSeq());
        log.debug("conferenceSttLog -> {}", conferenceSttLog);
        if (conferenceSttLog == null)
          createSttLog(data);
        if(lock.isLocked())
          lock.unlock();
      } else {
        throw new RuntimeException("Server is busy");
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void createSttLog(SttLogMessage.Data data) {
    ConferenceSttLog conferenceSttLog = ConferenceSttLog.builder()
        .conferenceId(data.getConferenceId())
        .endpointId(data.getEndpointId())
        .sentence(data.getSentence())
        .timestamp(data.getTimestamp())
        .seq(data.getSeq())
        .confidence(data.getConfidence())
        .build();
    conferenceSttLogRepository.save(conferenceSttLog);
    log.debug("conferenceSttLog created->{}", conferenceSttLog);
  }

}
