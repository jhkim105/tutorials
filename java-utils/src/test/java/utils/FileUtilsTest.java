package utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
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
}