package utils;

import org.apache.commons.lang3.StringUtils;

public final class VersionUtils {

  private VersionUtils() {}

  public static boolean isNew(String source, String target) {
    Version sourceVersion = Version.parse(source);
    Version targetVersion = Version.parse(target);

    return sourceVersion.compareTo(targetVersion) > 0 ;
  }

  public static int getMajorVersion(String version) {
    return Integer.parseInt(StringUtils.split(version, ".")[0]);
  }


}
