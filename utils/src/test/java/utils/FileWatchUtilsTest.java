package utils;

import org.junit.jupiter.api.Test;

class FileWatchUtilsTest {

  @Test
  void waitForCreated() {
    FileWatchUtils.waitForCreated("target", "1.txt");
  }

  @Test
  void waitForCreatedWithTimeout() {
    FileWatchUtils.waitForCreated("target", "1.txt", 30l);
  }

}