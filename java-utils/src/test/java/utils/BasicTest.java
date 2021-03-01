package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
public class BasicTest {

  @Test
  public void base64() {
    String plain = "https://www.remotemeeting.com/a?key=1234";
    String enc = Base64.getUrlEncoder().encodeToString(plain.getBytes());
    String dec = new String(Base64.getUrlDecoder().decode(enc));
    log.debug("enc:{}", enc);
    log.debug("dec:{}", dec);
    assertThat(plain).isEqualTo(dec);
  }
}
