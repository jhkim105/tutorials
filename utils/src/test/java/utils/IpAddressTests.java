package utils;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

class IpAddressTests {


  /**
   * <a href="https://namu.wiki/w/CIDR">wiki</a>
   * <a href="https://www.ipaddressguide.com/cidr">tool</a>
   */
  @Test
  void match() {
    IpAddressMatcher matcher = new IpAddressMatcher("10.0.0.1/24");
    assertThat(matcher.matches("10.0.0.2")).isTrue();

    matcher = new IpAddressMatcher("10.0.0.1");
    assertThat(matcher.matches("10.0.0.1")).isTrue();;
  }

}
