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


  @Test
  void startWith() {
    assertTrue(Pattern.matches("/RCCP/Web/.*", "/RCCP/Web/aaaa"));
    assertFalse(Pattern.matches("/RCCP/Web/.*", "RCCP/Web/aaaa"));
  }

  @Test
  void testEqual() {
    assertTrue(Pattern.matches("/RCCP/Web/aaaa", "/RCCP/Web/aaaa"));
    assertFalse(Pattern.matches("/RCCP/Web/aaaa", "/RCCP/Web/aaaab"));
  }

  @Test
  void testMqttTopic() {
//    assertTrue(Pattern.matches("/RCCP/CON/[^/]+/[^/]+", "/RCCP/CON/1/2"));
//    assertFalse(Pattern.matches("/RCCP/CON/[^/]+/[^/]+", "/RCCP/CON/1"));
//    assertFalse(Pattern.matches("/RCCP/CON/[^/]+/[^/]+", "/RCCP/CON/1/2/3"));


    assertTrue(Pattern.matches("/RCCP/CON/[a-zA-Z0-9-]+/(resource|life|report)$", "/RCCP/CON/6d6fdb290e3c49feb025b8dd30fb633e/resource"));
    assertFalse(Pattern.matches("/RCCP/CON/[a-zA-Z0-9-]+/(resource|life|report)$", "/RCCP/CON/6d6fdb290e3c49feb025b8dd30fb633e/abc"));

    assertFalse(Pattern.matches("/RCCP/CON/[a-zA-Z0-9-]+/(resource|life|report)$", "/RCCP/CON/6d6fdb290e3c49feb025b8dd30fb633e/abc"));

  }

  @Test
  void escapingRegex() {
    String regex = "/RCCP/CON/+";
    String escapedRegex = regex.replace("+", "\\+");
    assertTrue(Pattern.matches(escapedRegex, "/RCCP/CON/+"));
    assertFalse(Pattern.matches(escapedRegex, "/RCCP/CON/-"));

  }
}
