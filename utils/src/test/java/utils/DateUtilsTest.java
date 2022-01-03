package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class DateUtilsTest {

  @Test
  public void getTimestamp() {
    Date date = new Date();
    LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    assertEquals(DateUtils.getTimestamp(localDateTime), date.getTime());
  }
}