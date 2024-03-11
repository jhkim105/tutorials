package jhkim105.tutorials.redisson.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE) // need for jason deserialize
public class CurrentDate {

  private String pattern;

  private String date;

  public CurrentDate(String pattern, String date) {
    this.pattern = pattern;
    this.date = date;
  }

  public static CurrentDate create(String pattern) {
    return new CurrentDate(pattern, LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern)));
  }


}
