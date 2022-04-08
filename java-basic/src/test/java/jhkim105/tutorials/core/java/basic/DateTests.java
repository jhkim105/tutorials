package jhkim105.tutorials.core.java.basic;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;


public class DateTests {


  @Test
  void localDateTime() {
    System.out.println(LocalDateTime.now());
    System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS));
  }
}
