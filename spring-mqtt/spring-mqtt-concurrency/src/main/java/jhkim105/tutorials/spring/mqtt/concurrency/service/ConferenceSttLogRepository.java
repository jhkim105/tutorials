package jhkim105.tutorials.spring.mqtt.concurrency.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceSttLogRepository extends JpaRepository<ConferenceSttLog, String> {
  ConferenceSttLog getByConferenceIdAndSeq(String conferenceId, Integer seq);
}
