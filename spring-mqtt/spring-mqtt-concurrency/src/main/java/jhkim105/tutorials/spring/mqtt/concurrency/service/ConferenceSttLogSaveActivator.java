package jhkim105.tutorials.spring.mqtt.concurrency.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConferenceSttLogSaveActivator {

  @Autowired
  private ConferenceSttLogRepository conferenceSttLogRepository;

  @ServiceActivator
  public void handle(SttLogMessage message) {
    log.debug("handle start. message->{}", message);
    SttLogMessage.Data data = message.getData();
    if (message.isNotValid()) {
      log.debug("message is not valid. message -> {}", data);
      return;
    }
    try {
      createSttLog(data);
    } catch(RuntimeException e) {
      log.debug("ignored error:{}", e.toString());
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
