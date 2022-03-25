package utils;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class FileWatchUtilsTest {

  @Test
  @Disabled
  void waitForCreated() {
    FileWatchUtils.waitForCreated("target", "1.txt");
  }

  @Test
  @Disabled
  void waitForCreatedWithTimeout() {
    FileWatchUtils.waitForCreated("target", "1.txt", 30l);
  }

}