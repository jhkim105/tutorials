package utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class DownloaderTest {

  @Test
  void download() {
    Downloader.download("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target");
  }

  @Test
  void downloadWithJavaIO() {
    Downloader.downloadWithJavaIO("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target/test1.png");
  }

  @Test
  void downloadWithNIOFiles() {
    Downloader.downloadWithNIOFiles("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target/test2.png");
  }

  @Test
  void downloadWithNIOChannel() {
    Downloader.downloadWithNIOChannel("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target/test3.png");
  }

  @Test
  void downloadWithApacheCommons() {
    Downloader.downloadWithApacheCommons("https://www.remotemeeting.com/public/contents/src/img/main/img-reasonable-price-moneystack.png", "target/test4.png");
  }
}