package jhkim105.tutorials.regex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class MatcherTests {


  @Test
  void test() {
    Pattern stringPattern = Pattern.compile("\\d\\d\\d\\d");
    Matcher m = stringPattern.matcher("goodbye 2019 and welcome 2020");

    assertTrue(m.find());
    assertEquals(8, m.start());
    assertEquals("2019", m.group());
    assertEquals(12, m.end());

    assertTrue(m.find());
    assertEquals(25, m.start());
    assertEquals("2020", m.group());
    assertEquals(29, m.end());

    assertFalse(m.find());

  }

  @Test
  void emailDomain() {
    String regex = "(?<=@)[^.]+(?=\\.)";
    Matcher m = Pattern.compile(regex).matcher("abc.d.e@abc.co.kr");
    String domain = null;
    if (m.find()) {
      domain = m.group();
    }
    assertEquals(domain, "abc");
  }

}
