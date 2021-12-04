package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


@Slf4j
class BasicTest {

  @Test
  void base64() {
    String plain = "https://www.remotemeeting.com/a?key=1234";
    String enc = Base64.getUrlEncoder().encodeToString(plain.getBytes());
    String dec = new String(Base64.getUrlDecoder().decode(enc));
    log.debug("enc:{}", enc);
    log.debug("dec:{}", dec);
    assertThat(plain).isEqualTo(dec);
  }


  @Test
  void isNumber() {
    Assertions.assertFalse(NumberUtils.isNumber("038128"));
    Assertions.assertFalse(NumberUtils.isNumber("077595"));

    Assertions.assertTrue(NumberUtils.isParsable("038128"));
    Assertions.assertFalse(NumberUtils.isParsable("A38128"));
    Assertions.assertTrue(NumberUtils.isParsable("-38128"));
    Assertions.assertFalse(NumberUtils.isParsable("+38128"));

    Assertions.assertTrue(NumberUtils.isDigits("003128"));
    Assertions.assertFalse(NumberUtils.isDigits("-003128"));
  }
}
