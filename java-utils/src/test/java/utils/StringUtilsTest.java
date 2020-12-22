package utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StringUtilsTest {


  @Test
  public void removeEach() {
    String str = "abac,\"<abadfa> afef<script>";
    str = StringUtils.removeEach(str, "/:?*<>\"|&%+;'");
    log.debug("{}", str);
  }
}
