package jhkim105.tutorials.core.java.date;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Test;

class ZonedDateTimeTest {

  @Test
  void withZoneSameInstantAndSameLocal() {
    ZonedDateTime originalZonedDateTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
    ZonedDateTime newSameInstant = originalZonedDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"));
    ZonedDateTime newSameLocal = originalZonedDateTime.withZoneSameLocal(ZoneId.of("Asia/Seoul"));

    System.out.println("originalZonedDateTime: " + originalZonedDateTime);
    System.out.println("newSameInstant: " + newSameInstant);
    System.out.println("newSameLocal: " + newSameLocal);
  }


  @Test
  void convertLocalDateToZonedDateTime() {
    ZonedDateTime zonedDateTime = LocalDate.now().atStartOfDay(ZoneId.of("Asia/Seoul"));
    System.out.println(zonedDateTime);
  }

}
