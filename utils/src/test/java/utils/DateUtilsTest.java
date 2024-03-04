package utils;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void getTimestamp() {
    Date date = new Date();
    LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    assertEquals(DateUtils.getTimestamp(localDateTime), date.getTime());
  }

  @Test
  void convertStringToLocalDateTime() {
    String dateString = "20240101000000";
    String pattern = "yyyyMMddHHmmss";
    String timezone = "GMT";

    LocalDateTime localDateTime = DateUtils.convertToLocalDateTime(dateString, pattern, timezone);
    System.out.println(localDateTime);
    assertThat(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()).isEqualTo(1704067200000L);
  }

  @Test
  void convertTimestampToLocalDateTime() {
    String dateString = "20240101090000";
    String pattern = "yyyyMMddHHmmss";

    assertThat(DateUtils.convertToLocalDateTime(1704067200000L).format(DateTimeFormatter.ofPattern(pattern)))
        .isEqualTo(dateString);
  }

}