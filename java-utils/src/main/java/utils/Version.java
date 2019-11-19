package utils;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <major>.<minor>.<patch>-<status><count>
 */
@ToString
@Getter
@EqualsAndHashCode
public class Version implements Comparable<Version> {

  private int majorNumber;

  private int minorNumber;

  private int patchNumber;

  private String status;

  private int count;

  // <major>.<minor>.<patch>-<status><count>
  private final static Pattern VERSION_PATTERN
      = Pattern.compile("^(?:(\\d+)\\.)(\\d+)\\.(\\d+)?(?:[-]{1}([a-zA-Z]+)([\\d]+)?)?");

  private Version(int majorNumber, int minorNumber, int patchNumber, String status, int count) {
    this.majorNumber = majorNumber;
    this.minorNumber = minorNumber;
    this.patchNumber = patchNumber;
    this.status = status;
    this.count = count;
  }

  public static Version parse(String version) {
    Matcher matcher = VERSION_PATTERN.matcher(version);
    if(!matcher.matches())
      throw new IllegalArgumentException("Invalid version format(<major>.<minor>.<patch>-<status><count>).");

    return new Version(Integer.parseInt(matcher.group(1)),
          Integer.parseInt(matcher.group(2)),
          Integer.parseInt(matcher.group(3)),
          matcher.group(4),
        StringUtils.isBlank(matcher.group(5))? 0 : Integer.parseInt(matcher.group(5))
        );
  }

  @Override
  public int compareTo(Version other) {
    int diff = this.majorNumber - other.majorNumber;
    if (diff != 0)
      return diff;

    diff = this.minorNumber - other.minorNumber;
    if (diff != 0)
      return diff;

    diff = this.patchNumber - other.patchNumber;
    if (diff != 0)
      return diff;

    if (diff !=0 )
      return diff;

    return this.count - other.count;
}
}
