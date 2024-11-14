package jhkim105.tutorials.jpatime;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

@Entity
@ToString
public class User {

  @Id
  @UuidGenerator
  @Getter
  private String id;

  private Date date;

  private LocalDateTime localDateTime;

  private ZonedDateTime zonedDateTime;

  private OffsetDateTime offsetDateTime;
  private Instant instant;
  private OffsetTime offsetTime;
  private OffsetTime offsetTimeUtc;
  private OffsetTime offsetTimeSystemDefault;

  public User() {
    this.date = new Date();
    this.localDateTime = LocalDateTime.now();
    this.zonedDateTime = ZonedDateTime.now();
    this.offsetDateTime = OffsetDateTime.now();
    this.instant = Instant.now();
    this.offsetTime = offsetDateTime.toOffsetTime();
    this.offsetTimeUtc = instant.atOffset(ZoneOffset.UTC).toOffsetTime();
    this.offsetTimeSystemDefault = instant.atZone(ZoneId.systemDefault()).toOffsetDateTime().toOffsetTime();
  }

  public User(LocalDateTime localDateTime, String timezone) {
    this.date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    this.localDateTime = localDateTime;
    ZoneId zoneId = ZoneId.of(timezone);
    zonedDateTime = localDateTime.atZone(zoneId);
    offsetDateTime = localDateTime.atOffset(zoneId.getRules().getOffset(localDateTime));
    instant = zonedDateTime.toInstant();
  }

}
