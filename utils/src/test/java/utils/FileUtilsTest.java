package utils;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class FileUtilsTest {

  @Test
  void getDirPath() {
    assertEquals(FileUtils.getDirPath("a/b/c/d.exe"), "a/b/c");
    assertEquals(FileUtils.getDirPath("a/b/c/"), "a/b/c");
  }


  @Test
  void hasPathTraversal() {
    assertFalse(FileUtils.hasTraversalPath("/DATA/WEB/remotemmeting/storage/document"));
    assertTrue(FileUtils.hasTraversalPath("/DATA/WEB/remotemmeting/storage/document/../../storage"));
  }

  @Test
  void fileList() {
    List<File> fileList = FileUtils.files("src/test/java/utils");
    assertThat(fileList).isNotEmpty();
  }

  @Test
  void findOldFiles() {
    Iterator<File> oldFiles = FileUtils.findOldFiles(new File("target"), 1);
    List<File> list = new ArrayList<>();
    oldFiles.forEachRemaining(list::add);
    log.debug("{}", list);
  }

  @Test
  void findOldDirs() {
    List<File> oldDirs = FileUtils.findOldDirs("target",  "utils",0, "classes");
    log.debug("{}", oldDirs);
  }

  @Test
  void delete() {
    String basePath = "target/temp";

    FileUtils.mkdir(basePath + "/1");
    FileUtils.mkdir(basePath + "/2");
    FileUtils.mkdir(basePath + "/3");

    FileUtils.deleteEmptyDirs(basePath);

    Assertions.assertThat(Paths.get(basePath).toFile().listFiles().length).isEqualTo(0);
  }

  @Test
  void findFileEncoding() {
    assertThat(FileUtils.findFileEncoding(new File("src/test/resources/encoding-EUC-KR.txt"))).isEqualTo("EUC-KR");
    assertThat(FileUtils.findFileEncoding(new File("src/test/resources/encoding-UTF-8.txt"))).isEqualTo("UTF-8");
  }

  @Test
  void getFileListOrderByFileName() {
    List<File> fileList = FileUtils.getFileListOrderByFileName("src/test/resources");
    log.debug("fileList: {}", fileList);
  }

  @Test
  void moveFile() {
    FileUtils.moveFile("src/test/resources/input.txt", "src/test/resources/output.txt");
    FileUtils.moveFile("src/test/resources/output.txt", "src/test/resources/input.txt");
  }

  @Test
  void isEmptyDir() {
    assertThat(FileUtils.isEmptyDir("src/test/resources")).isEqualTo(false);
    FileUtils.mkdir("target/empty");
    FileUtils.mkdir("target/empty/a");
    assertThat(FileUtils.isEmptyDir("target/empty")).isEqualTo(true);
  }

  @Test
  void contentType() {
    assertThat(FileUtils.contentType(Paths.get("src/test/resources/input.txt"))).isEqualTo(ContentType.TEXT_PLAIN.getMimeType());
    assertThat(FileUtils.contentType(Paths.get("src/test/resources/image.png"))).isEqualTo(ContentType.IMAGE_PNG.getMimeType());
    assertThat(FileUtils.contentType(Paths.get("src/test/resources/info.zip"))).isEqualTo("application/zip");
  }
}