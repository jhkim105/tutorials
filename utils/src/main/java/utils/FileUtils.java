package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.web.multipart.MultipartFile;


public class FileUtils {

  public static String getDirPath(String filePath) {
    return FilenameUtils.getFullPathNoEndSeparator(filePath);
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
    mkdir(uploadDir);

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

  public static void mkdir(String dirPath) {
    File dir = new File(dirPath);
    try {
      org.apache.commons.io.FileUtils.forceMkdir(dir);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static void moveFile(String source, String dest) {
    try {
      org.apache.commons.io.FileUtils.moveFile(new File(source), new File(dest));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static void writeStringToFile(File file, String data) {
    try {
      org.apache.commons.io.FileUtils.writeStringToFile(file, data, "UTF-8");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static String readFileToString(File file) {
    try {
      return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  public static List<File> files(String dir) {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return stream.map(p -> p.toFile())
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<Path> filePaths(String dir) {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return stream.collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void deleteContents(String dir) {
    org.aspectj.util.FileUtil.deleteContents(new File(dir));
  }

  public static Iterator<File> findOldFiles(File dir, int days) {
    Date cutoff = DateUtils.addDays(new Date(), -days);
    IOFileFilter fileFilter = FileFilterUtils.ageFileFilter(cutoff);
    return org.apache.commons.io.FileUtils.iterateFiles(dir, fileFilter, fileFilter);
  }

  public static List<File> findOldDirs(String dir, String dirName, int hours, String... excludes) {
    long cutoff = Timestamp.valueOf(LocalDateTime.now().minusHours(hours)).getTime();;
    try(Stream<Path> pathStream = Files.walk(Paths.get(dir))) {
      return pathStream
          .map(Path::toFile)
          .filter(File::isDirectory)
          .filter(file -> file.getName().equals(dirName)
              && Arrays.stream(excludes).noneMatch(exclude ->file.getPath().contains(exclude)))
          .filter(file -> file.lastModified() < cutoff)
          .collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void deleteEmptyDirs(String dir) {
    try(Stream<Path> pathStream = Files.walk(Paths.get(dir))) {
      pathStream
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .filter(File::isDirectory)
          .filter(f -> !StringUtils.endsWith(dir, f.getName()))
          .forEach(File::delete);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void deleteDirectory(String dirPath) {
    try {
      org.apache.commons.io.FileUtils.deleteDirectory(new File(dirPath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String findFileEncoding(File file) {
    byte[] buf = new byte[4096];
    try {
      FileInputStream fis = new FileInputStream(file);
      UniversalDetector detector = new UniversalDetector(null);

      int length;
      while ((length = fis.read(buf)) > 0 && !detector.isDone()) {
        detector.handleData(buf, 0, length);
      }
      detector.dataEnd();

      String encoding = detector.getDetectedCharset();
      detector.reset();

      return encoding;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static List<File> getFileListOrderByFileName(String dirPath) {
    File dir = new File(dirPath);
    return Stream.of(Objects.requireNonNull(dir.listFiles()))
        .filter(File::isFile)
        .sorted(Comparator.comparing(f -> FilenameUtils.getBaseName(f.getName())))
        .collect(Collectors.toList());
  }

  public static boolean isEmptyDir(String dir) {
    Path path = Paths.get(dir);
    if (!Files.isDirectory(path)) {
      throw new IllegalArgumentException("This is not directory.");
    }

    try (Stream<Path> entries = Files.list(path)) {
      return entries.noneMatch(p -> p.toFile().isFile());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String contentType(Path path) {
    try {
      return Files.probeContentType(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
