package utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DownloaderTest {

  @Test
  void download() {
    Downloader.download("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target/test.png");
  }

}