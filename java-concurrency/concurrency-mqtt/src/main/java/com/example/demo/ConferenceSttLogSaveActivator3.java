package com.example.demo;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConferenceSttLogSaveActivator3 {

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
      if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
        String executeKey = key + "_";
        if (isExecuted(executeKey)) {
          log.debug("Already processed. key->{}", key);
        } else {
          createSttLog(data);
          setExecuted(executeKey);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      if (lock.isLocked())
        lock.unlock();
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


  private boolean isExecuted(String key) {
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    long currentValue = atomicLong.get();
    log.debug("isExecuted().currentValue:{}", currentValue);
    return currentValue > 0;
  }


  private void setExecuted(String key) {
    RAtomicLong atomicLong = redissonClient.getAtomicLong(key);
    long currentValue = atomicLong.addAndGet(1);
    log.debug("setExecuted().currentValue:{}", currentValue);
    if (atomicLong.remainTimeToLive() < 0) {
      atomicLong.expire(10, TimeUnit.SECONDS);
    }
  }

}
