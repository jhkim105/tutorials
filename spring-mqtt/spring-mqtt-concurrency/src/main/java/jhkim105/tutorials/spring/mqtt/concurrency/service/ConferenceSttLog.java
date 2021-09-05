package jhkim105.tutorials.spring.mqtt.concurrency.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "lt_conference_stt_log", indexes= {@Index(name = "idx_csl_conference_id", columnList = "conference_id, timestamp")},
    uniqueConstraints = {@UniqueConstraint(name = "uk_csl_conference_id", columnNames = {"conference_id", "seq"})
})
@EqualsAndHashCode(callSuper = false, of = "id")
@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings(value = { "PMD.UnusedPrivateField", "PMD.SingularField" })
public class ConferenceSttLog {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid2")
  @Column(length = 50)
  private String id;

  @Column(name = "conference_id", length = 50)
  private String conferenceId;

  @Column(name = "endpoint_id", length = 50)
  private String endpointId;

  private Integer seq;

  private Long timestamp; //ms
  @Lob
  private String sentence;

  private Double confidence;

  @Builder
  public ConferenceSttLog(String conferenceId, String endpointId, Integer seq, Long timestamp, String sentence, Double confidence) {
    this.conferenceId = conferenceId;
    this.endpointId = endpointId;
    this.seq = seq;
    this.timestamp = timestamp;
    this.sentence = sentence;
    this.confidence = confidence;
  }
}
