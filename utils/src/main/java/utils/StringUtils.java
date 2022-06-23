package utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

  public static String removeEach(String str, String format) {
    char[] chars = format.toCharArray();

    for (char c : chars) {
      str = org.apache.commons.lang3.StringUtils.remove(str, c);
    }

    return str;
  }

  public static String defaultString(String str) {
    return org.apache.commons.lang3.StringUtils.defaultString(str);
  }

  public static String defaultString(String str, String defaultStr) {
    return org.apache.commons.lang3.StringUtils.defaultString(str, defaultStr);
  }

  public static boolean isBlank(String str) {
    return org.apache.commons.lang3.StringUtils.isBlank(str);
  }

  public static String getDecimalFormatString(Object value, String format) {
    DecimalFormat decimalFormat = new DecimalFormat(format);
    return decimalFormat.format(value);
  }

  public static String removeBefore(String str, String separator ) {
    String removeStr = org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
    return org.apache.commons.lang3.StringUtils.removeStart(str, removeStr);
  }


  public static String substringAfter(String str, String separator ) {
    return org.apache.commons.lang3.StringUtils.substringAfter(str, separator);
  }
  public static String substringBefore(String str, String separator ) {
    return org.apache.commons.lang3.StringUtils.substringBefore(str, separator);
  }

  public static boolean endsWith(String str, String suffix) {
    return org.apache.commons.lang3.StringUtils.endsWith(str, suffix);
  }

  public static String getEmailDomain(String email) {
    String regex = "(?<=@)[^.]+(?=\\.)";
    Matcher m = Pattern.compile(regex).matcher(email);
    String domain = null;
    if (m.find()) {
      domain = m.group();
    }
    return domain;
  }

}
