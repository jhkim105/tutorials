package utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

  public static String getDateString(Date date, String pattern) {
    return getDateString(convertToLocalDateTime(date), pattern);
  }

  public static String getDateString(String pattern) {
    return getDateString(LocalDateTime.now(), pattern);
  }

  public static String getDateString(LocalDateTime localDateTime, String pattern) {
    return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static LocalDateTime convertToLocalDateTime(Date date) {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static Date addDays(Date date, int i) {
    if (date == null)
      return null;
    return org.apache.commons.lang3.time.DateUtils.addDays(date, i);
  }

  public static long getEpochMilli(LocalDateTime localDateTime) {
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }
}

