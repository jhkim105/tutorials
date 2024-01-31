package jhkim105.tutorials.uuid;

import com.github.f4b6a3.uuid.UuidCreator;
import com.github.f4b6a3.uuid.alt.GUID;
import org.junit.jupiter.api.Test;

class TimeBasedUUIDTest {




  /**
   * <a href="https://github.com/f4b6a3/uuid-creator">uuid-creator</a>
   */
  @Test
  void uuidCreator() {
    System.out.println("v1: " + UuidCreator.getTimeBased());
    System.out.println("v6: " + UuidCreator.getTimeOrdered());
    System.out.println("v7: " + UuidCreator.getTimeOrderedEpoch());

    // Alternative - GUID
    System.out.println("v1: " + GUID.v1().toUUID());
  }
}
