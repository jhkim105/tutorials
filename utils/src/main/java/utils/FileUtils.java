package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;


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

  public static File upload(MultipartFile multipartFile, String uploadDir, String saveFileName) {
    makeDirIfNotExists(uploadDir);

    String fileName = saveFileName;
    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
    if (StringUtils.isNotBlank(extension)) {
      fileName += "." + extension;
    }
    String targetFilePath = uploadDir + File.separator + fileName;
    File targetFile = new File(targetFilePath);
    try {
      multipartFile.transferTo(targetFile);
    } catch (IOException ex) {
      throw new RuntimeException(String.format("file upload error:%s", targetFilePath), ex);
    }
    return targetFile;
  }

  public static File upload(MultipartFile multipartFile, String uploadDir) {
    String saveFileName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());
    return upload(multipartFile, uploadDir, saveFileName);
  }

  public static void makeDirIfNotExists(String path) {
    File dirPath = new File(path);
    if (!dirPath.exists()) {
      boolean made = dirPath.mkdirs();
      if (!made) {
        throw new RuntimeException(String.format("make directory(%s) fail", path));
      }
    }
  }

  public static void writeStringToFile(File file, String data) {
    try {
      org.apache.commons.io.FileUtils.writeStringToFile(file, data, "UTF-8");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static List<File> files(String dir) {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return stream.map( p -> p.toFile())
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Path> paths(String dir) {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return stream.collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void deleteContents(String dir) {
    org.aspectj.util.FileUtil.deleteContents(new File(dir));
  }
}
