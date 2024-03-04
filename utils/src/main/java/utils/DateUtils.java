package utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

  public static long getTimestamp(LocalDateTime localDateTime) {
    return Timestamp.valueOf(localDateTime).getTime();
  }

  public static Date convertToDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date convertToDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDateTime convertToLocalDateTime(String dateString, String pattern, String timezone) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    ZoneId zoneId = ZoneId.of(timezone);
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter.withZone(zoneId));
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(zonedDateTime.toInstant().toEpochMilli()), ZoneId.systemDefault());
  }

  public static LocalDateTime convertToLocalDateTime(long timestamp) {
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
  }

}

