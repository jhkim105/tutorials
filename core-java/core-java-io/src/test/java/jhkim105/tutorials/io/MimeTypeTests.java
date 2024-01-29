package jhkim105.tutorials.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.Test;

/**
 * Tika: 파일 메타데이터로 mimeType 결정
 * Files.probeContentType(): 파일 확장자로 mimeType 결정
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
 */

class MimeTypeTests {

  @Test
  void tika_pptx() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    Tika tika = new Tika();
    String type = tika.detect(path.toFile());
    assertThat(type).isEqualTo(expected);

    MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(type);
    assertThat(mimeType.toString()).isEqualTo(expected);
  }

  @Test
  void tika_pptx_no_extension() throws Exception {
    Path path = Paths.get("src/test/resources/sample_pptx");
    String expected = "application/x-tika-ooxml";
    Tika tika = new Tika();
    String type = tika.detect(path.toFile());
    assertThat(type).isEqualTo(expected);
  }

  @Test
  void tika_확장자없는경우() throws Exception {
    Tika tika = new Tika();
    String type = tika.detect(Paths.get("src/test/resources/img").toFile());
    assertThat(type).isEqualTo("image/png");
  }

  @Test
  void tika_hwpx() throws Exception {
    Path path = Paths.get("src/test/resources/sample.hwpx");
    Tika tika = new Tika();
    assertThat(tika.detect(path.toFile())).isEqualTo("application/hwp+zip");
    assertThat(tika.detect(path.toFile().getName())).isEqualTo("application/hwp+zip");
  }

  @Test
  void tika_hwpx_no_extension() throws Exception {
    Path path = Paths.get("src/test/resources/sample_hwpx");
    Tika tika = new Tika();
    assertThat(tika.detect(path.toFile())).isEqualTo("application/zip");
  }

  @Test
  void probeContentType() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo(expected);
  }

  @Test
  void probeContentType_hwpx_then_null() throws Exception {
    Path path = Paths.get("src/test/resources/sample.hwpx");
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo(null);
  }

  @Test
  void probeContentType_확장자변경한경우_확장자로인식함() throws Exception {
    Path path = Paths.get("src/test/resources/img-png.jpg");
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo("image/jpeg");
    System.out.println(type);
  }

  @Test
  void probeContentType_확장자없는경우_null() throws Exception {
    Path path = Paths.get("src/test/resources/img");
    String type = Files.probeContentType(path);
    assertThat(type).isNull();
  }


}
