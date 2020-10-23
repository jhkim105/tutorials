package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class XssUtils {

  private static final Pattern NULL_CHAR = Pattern.compile("\0");
  private static final Pattern[] PATTERNS =
      new Pattern[]{
          Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),

          Pattern.compile(
              "src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
              Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

          Pattern.compile(
              "src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
              Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
          Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),

          Pattern.compile("<iframe>(.*?)</iframe>", Pattern.CASE_INSENSITIVE),

          Pattern.compile(
              "<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

          Pattern.compile(
              "<img(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

          Pattern.compile(
              "eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

          Pattern.compile(
              "expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),

          Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),

          Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),

          Pattern.compile(
              "on(load|error|mouseover|submit|reset|focus|click)(.*?)=",
              Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
      };

  public static boolean containsXSS(String value) {
    if (value != null) {

      // Avoid null characters
      String cleanValue = NULL_CHAR.matcher(value).replaceAll("");

      // Remove all sections that match a pattern
      for (Pattern scriptPattern : PATTERNS) {
        Matcher matcher = scriptPattern.matcher(cleanValue);
        if (matcher.find()) {
          log.warn("Potentially malicious XSS script found: {}", cleanValue);
          return true;
        }
      }
    }
    return false;
  }

  public static String removeXss(String value) {

    if (value == null) {
      return null;
    }

    // Avoid null characters
    String cleanValue = NULL_CHAR.matcher(value).replaceAll("");
    if (StringUtils.isBlank(cleanValue)) {
      return cleanValue;
    }

    // Remove all sections that match a pattern
    for (Pattern scriptPattern : PATTERNS) {
      Matcher matcher = scriptPattern.matcher(cleanValue);
      cleanValue = matcher.replaceAll("");
    }
    return cleanValue;
  }
}