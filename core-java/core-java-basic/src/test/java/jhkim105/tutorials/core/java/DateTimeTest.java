package jhkim105.tutorials.core.java;

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
  void test() throws Exception {
    String dateTimeString = "2024-02-06 12:30:00"; // 예시 날짜 및 시간 문자열
    String timezone = "Asia/Seoul"; // 예시 시간대
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone(timezone)); // 시간대 설정
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


}
