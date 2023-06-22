package jhkim105.tutorials.jsoup;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.junit.jupiter.api.Test;

@Slf4j
class JsoupTests {


  @Test
  void xss() {
    String content = "<img src=\"https://www.remotemeeting.com/public/contents/src/img/logo/logo-main-white.svg\" onerror=\"alert(1)\">";

    assertThat(Jsoup.clean(content, Safelist.basicWithImages()))
        .isEqualTo("<img src=\"https://www.remotemeeting.com/public/contents/src/img/logo/logo-main-white.svg\">");

    assertThat(Jsoup.clean("<img src=\"abc\" onerror=\"alert(1)\">", Safelist.basicWithImages()))
        .isEqualTo("<img>");
  }




}
