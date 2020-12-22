package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import org.apache.commons.io.FilenameUtils;


public class FileUtils {

  public static String getDirPath(String filePath) {
    if (filePath == null) {
      return null;
    }
    int index = FilenameUtils.indexOfLastSeparator(filePath);
    return filePath.substring(0, index);
  }

  public static boolean hasTraversalPath(String filePath) {
    File file = Paths.get(filePath).toFile();
    String canonicalPath;
    try {
      canonicalPath = file.getCanonicalPath();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    String absolutePath = file.getAbsolutePath();
    return !canonicalPath.equals(absolutePath);
  }
}
