package utils;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

@Slf4j
class StringUtilsTest {


  @Test
  void removeEach() {
    String str = "abac,\"<abadfa> afef<script>";
    str = StringUtils.removeEach(str, "/:?*<>\"|&%+;'");
    log.debug("{}", str);
  }

  @Test
  void getDecimalFormat() {
    AssertionsForClassTypes.assertThat(StringUtils.getDecimalFormatString(1, "00")).isEqualTo("01");
  }

  @Test
  void removeBefore() {
    Assertions.assertThat(StringUtils.removeBefore("/Users/jihwankim/dev/rsupport/rm/WebServer/storage/abc/bd", "/storage"))
        .isEqualTo("/storage/abc/bd");
  }

  @Test
  void substringBefore() {
    Assertions.assertThat(StringUtils.substringBefore("/Users/jihwankim/dev/rsupport/rm/WebServer/storage/abc/bd", "/storage"))
        .isEqualTo("/Users/jihwankim/dev/rsupport/rm/WebServer");
  }

  @Test
  void substringAfter() {
    Assertions.assertThat(StringUtils.substringAfter("/Users/jihwankim/dev/rsupport/rm/WebServer/storage/abc/bd", "/storage"))
        .isEqualTo("/abc/bd");
  }

  @Test
  void getEmailDomain() {
    Assertions.assertThat(StringUtils.getEmailDomain("abc_1.2@abc-d.e.org")).isEqualTo("abc-d");
  }
}
