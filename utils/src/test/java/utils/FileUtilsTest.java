package utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class FileUtilsTest {

  @Test
  void getDirPath() {
    assertEquals(FileUtils.getDirPath("a/b/c/d.exe"), "a/b/c");
  }


  @Test
  void hasPathTraversal() {
    assertFalse(FileUtils.hasTraversalPath("/DATA/WEB/remotemmeting/storage/document"));
    assertTrue(FileUtils.hasTraversalPath("/DATA/WEB/remotemmeting/storage/document/../../storage"));
  }

  @Test
  void fileList() {
    List<File> fileList = FileUtils.files("src/test/java/utils");
    Assertions.assertThat(fileList).isNotEmpty();
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

    FileUtils.makeDirectory(basePath + "/1");
    FileUtils.makeDirectory(basePath + "/2");
    FileUtils.makeDirectory(basePath + "/3");

    FileUtils.deleteEmptyDirs(basePath);

    Assertions.assertThat(Paths.get(basePath).toFile().listFiles().length).isEqualTo(0);
  }

  @Test
  void findFileEncoding() {
    Assertions.assertThat(FileUtils.findFileEncoding(new File("src/test/resources/encoding-EUC-KR.txt"))).isEqualTo("EUC-KR");
    Assertions.assertThat(FileUtils.findFileEncoding(new File("src/test/resources/encoding-UTF-8.txt"))).isEqualTo("UTF-8");
  }

}