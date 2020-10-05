package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConferenceSttLogSaveActivator {

  @Autowired
  private ConferenceSttLogRepository conferenceSttLogRepository;

  @Autowired
  private ConcurrencyUtils concurrencyUtils;

  @ServiceActivator
  public void handle(SttLogMessage message) {
    log.debug("handle start. message->{}", message);
    SttLogMessage.Data data = message.getData();
    if (message.isNotValid()) {
      log.debug("message is not valid. message -> {}", data);
      return;
    }

    String key = String.format("%s_%s", data.getConferenceId(), data.getSeq());
    if(concurrencyUtils.isExecuted(key)) {
      log.debug("Already processed. key->{}", key);
      return;
    }

    ConferenceSttLog conferenceSttLog = ConferenceSttLog.builder()
        .conferenceId(data.getConferenceId())
        .endpointId(data.getEndpointId())
        .sentence(data.getSentence())
        .timestamp(data.getTimestamp())
        .seq(data.getSeq())
        .confidence(data.getConfidence())
        .build();

    conferenceSttLogRepository.save(conferenceSttLog);
    log.debug("conferenceSttLog->{}", conferenceSttLog);
  }

}
