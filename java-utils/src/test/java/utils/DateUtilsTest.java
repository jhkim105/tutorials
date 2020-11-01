package utils;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import junit.framework.TestCase;
import org.junit.Test;

public class DateUtilsTest {

  @Test
  public void getEpochMilli() {
    Date date = new Date();
    LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    assertEquals(DateUtils.getEpochMilli(localDateTime), date.getTime());
  }
}