package jhkim105.tutorials.core.java.date;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

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


}
