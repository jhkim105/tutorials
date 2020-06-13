package utils;

import org.apache.commons.io.FilenameUtils;


public class FileUtils {

  public static String getDirPath(String filePath) {
    if (filePath == null) {
      return null;
    }
    int index = FilenameUtils.indexOfLastSeparator(filePath);
    return filePath.substring(0, index);
  }
}
