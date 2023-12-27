package jhkim105.tutorials.io;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.Test;


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
  void usingJava7() throws Exception {
    Path path = Paths.get("src/test/resources/sample.pptx");
    String type = Files.probeContentType(path);
    assertThat(type).isEqualTo(expected);
  }


}
