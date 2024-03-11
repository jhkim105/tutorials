package jhkim105.tutorials.redisson.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public record CurrentDateRecord(
    String pattern,
    String date
) {

  public static CurrentDateRecord create(String pattern) {
    return new CurrentDateRecord(pattern, LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)));
  }


}
