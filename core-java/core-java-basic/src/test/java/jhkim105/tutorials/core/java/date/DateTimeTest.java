package jhkim105.tutorials.core.java.date;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


@Disabled
class DateTimeTest {

  @Test
  void dateTime() throws Exception {
    String dateTimeString = "2024-02-06 12:30:00";
    String timezone = "Asia/Seoul";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
    Date date = dateFormat.parse(dateTimeString);
    System.out.println("Created Date: " + date);

    // LocalDateTime
    LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    System.out.println("LocalDateTime: " + localDateTime);

    // ZonedDateTime
    ZonedDateTime zonedDateTime = ZonedDateTime.of(localDateTime, ZoneId.of(timezone));
    System.out.println("ZonedDateTime: " + zonedDateTime);

    // OffsetDateTime
    OffsetDateTime offsetDateTime = OffsetDateTime.of(localDateTime, ZoneId.of(timezone).getRules().getOffset(localDateTime));
    System.out.println("OffsetDateTime: " + offsetDateTime);
  }

  @Test
  void zonedDateTime() {
    ZonedDateTime now = ZonedDateTime.now();
    System.out.println(now);
    System.out.println(now.getZone());
    System.out.println(now.toLocalDateTime());
    System.out.println(now.withZoneSameInstant(ZoneId.of("UTC")));
    System.out.println(now.withZoneSameInstant(ZoneId.of("UTC")).toLocalTime());
  }

  @Test
  void truncate() {
    System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
    assertThat(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS))
        .isEqualTo(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0));
  }

  @Test
  void localDateTime() {
    LocalDateTime now = LocalDateTime.now();
    ZoneId zoneId = ZoneId.of("UTC");
    System.out.println(ZonedDateTime.of(now, zoneId));
    System.out.println(now.atZone(zoneId));
    assertThat(ZonedDateTime.of(now, zoneId)).isEqualTo(now.atZone(zoneId));
  }

  @Test
  void localDate() {
    LocalDate now = LocalDate.now();
    ZoneId zoneId = ZoneId.of("Asia/Seoul");
    System.out.println(ZonedDateTime.of(now.atStartOfDay(), zoneId));
    System.out.println(now.atStartOfDay(zoneId));
    assertThat(ZonedDateTime.of(now.atStartOfDay(), zoneId)).isEqualTo(now.atStartOfDay(zoneId));
  }


  @Test
  void instant() {
    Instant now = Instant.now();
    System.out.println(now);
  }

}
