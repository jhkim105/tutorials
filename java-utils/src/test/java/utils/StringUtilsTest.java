package utils;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringUtilsTest {


  @Test
  public void removeEach() {
    String str = "abac,\"<abadfa> afef<script>";
    str = StringUtils.removeEach(str, "/:?*<>\"|&%+;'");
    log.debug("{}", str);
  }

  @Test
  public void getDecimalFormat() {
    AssertionsForClassTypes.assertThat(StringUtils.getDecimalFormatString(1, "00")).isEqualTo("01");
  }
}
