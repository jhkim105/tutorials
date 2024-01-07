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
 */

class MimeTypeTests {

  Path path = Paths.get("src/test/resources/sample.pptx");
  String expected = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

  @Test
  void tika() throws Exception {
    Tika tika = new Tika();
    String type = tika.detect(path.toFile());
    assertThat(type).isEqualTo(expected);

    MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(type);
    assertThat(mimeType.toString()).isEqualTo(expected);
  }

  @Test
  void tika_확장자변경한경우_파일내용으로인식함() throws Exception {
    Tika tika = new Tika();
    String type = tika.detect(Paths.get("src/test/resources/img-png.jpg").toFile());
    assertThat(type).isEqualTo("image/png");
  }

  @Test
  void tika_확장자없는경우() throws Exception {
    Tika tika = new Tika();
    String type = tika.detect(Paths.get("src/test/resources/img").toFile());
    assertThat(type).isEqualTo("image/png");
  }



  @Test
  void usingJava7() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo(expected);
  }

  @Test
  void usingJava7_확장자변경한경우_확장자로인식함() throws Exception {
    Path path = Paths.get("src/test/resources/img-png.jpg");
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo("image/jpeg");
    System.out.println(type);
  }

  @Test
  void usingJava7_확장자없는경우_null() throws Exception {
    Path path = Paths.get("src/test/resources/img");
    String type = Files.probeContentType(path);
    assertThat(type).isNull();
  }


}
