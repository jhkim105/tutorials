package utils;

public class StringUtils {

  public static String removeEach(String str, String format) {
    char[] chars = format.toCharArray();

    for (char c : chars) {
      str = org.apache.commons.lang3.StringUtils.remove(str, c);
    }

    return str;
  }
}
