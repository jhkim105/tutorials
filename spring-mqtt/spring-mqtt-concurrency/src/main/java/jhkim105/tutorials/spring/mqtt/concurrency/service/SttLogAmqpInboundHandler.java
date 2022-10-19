package jhkim105.tutorials.spring.mqtt.concurrency.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SttLogAmqpInboundHandler {

  private final ConferenceSttLogRepository conferenceSttLogRepository;

  @ServiceActivator
  public void handle(SttLogMessage message) {
    log.debug("handle start. message->{}", message);
    SttLogMessage.Data data = message.getData();
    createSttLog(data);

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
