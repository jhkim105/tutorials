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
 * - stream 으로 읽으면 제대로 못읽는 경우가 있다. (parser 를 추가해야 정상 동작)
 * Files.probeContentType(): 파일 확장자로 mimeType 결정
 * https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
 */

class MimeTypeTests {

  @Test
  void tika_pptx() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    Tika tika = new Tika();
    String type = tika.detect(path);
    assertThat(type).isEqualTo(expected);

    MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(type);
    assertThat(mimeType.toString()).isEqualTo(expected);
  }

  @Test
  void tika_pptx_stream() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
//    String expected = "application/x-tika-ooxml"; // parser 등록 안한 경우
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    Tika tika = new Tika();
    String type = tika.detect(Files.newInputStream(path));
    assertThat(type).isEqualTo(expected);
  }

  @Test
  void tika_pptx_google_stream() throws Exception {
    Path path = Paths.get("src/test/resources/google.pptx");
//    String expected = "application/zip"; // parser 등록 안한 경우
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    Tika tika = new Tika();
    String type = tika.detect(Files.newInputStream(path));
    assertThat(type).isEqualTo(expected);
  }

  @Test
  void tika_pptx_google() throws Exception {
    Path path = Paths.get("src/test/resources/google.pptx");
    String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    Tika tika = new Tika();
    String type = tika.detect(path);
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
    assertThat(tika.detect(Files.newInputStream(path))).isEqualTo("application/hwp+zip");
    assertThat(tika.detect(path)).isEqualTo("application/hwp+zip");
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
  void probeContentType_확장자없는경우_null() throws Exception {
    Path path = Paths.get("src/test/resources/img");
    String type = Files.probeContentType(path);
    assertThat(type).isNull();
  }


}
