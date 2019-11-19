package utils;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class VersionUtilsTest {

  @Test
  public void isNew() {
    assertTrue(VersionUtils.isNew("1.0.1-b121", "1.0.0-b120"));
    assertTrue(VersionUtils.isNew("1.0.1-b111", "1.0.1-b22"));
    assertTrue(VersionUtils.isNew("1.0.1", "1.0.0"));
    assertTrue(VersionUtils.isNew("1.0.3-b11", "1.0.2"));
  }


  @Test
  public void equals() {
    assertTrue(Version.parse("1.0.1-b121").equals(Version.parse("1.0.1-b121")));
  }

  @Test
  public void majorVersion() {
    assertTrue(VersionUtils.getMajorVersion("2.231.1-b1411") == 2);
    assertTrue(VersionUtils.getMajorVersion("1.11.32(r1411)") == 1);
  }

}