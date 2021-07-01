package utils;

import java.text.DecimalFormat;

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

  public static boolean isBlank(String str) {
    return org.apache.commons.lang3.StringUtils.isBlank(str);
  }

  public static String getDecimalFormatString(Object value, String format) {
    DecimalFormat decimalFormat = new DecimalFormat(format);
    return decimalFormat.format(value);
  }
}
