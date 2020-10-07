package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ConferenceSttLogSaveActivator2 {

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
    if(isExecuted(key)) {
      log.debug("Already processed. key->{}", key);
      return;
    }

    createSttLog(data);
  }

  private boolean isExecuted(String key) {
    boolean executed = false;
    log.debug("executeOnce called.");
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    long currentValue = atomicLong.addAndGet(1);
    log.debug("currentValue:{}", currentValue);
    if (currentValue > 1) {
      executed = true;
    }

    if (atomicLong.remainTimeToLive() < 0) {
      atomicLong.expire(5, TimeUnit.SECONDS);
    }
    return executed;
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
