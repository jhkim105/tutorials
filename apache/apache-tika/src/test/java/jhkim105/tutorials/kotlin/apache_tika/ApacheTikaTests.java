package jhkim105.tutorials.kotlin.apache_tika;


import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.junit.jupiter.api.Test;


@Slf4j
class ApacheTikaTests {



  /**
   *  https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Common_types
   */
  @Test
  void detectMimeType() throws Exception {
    Tika tika = new Tika();

    Path path = Paths.get("src/test/resources/sample.pptx");
    String type = tika.detect(path.toFile());
    log.info(type);

    MimeType mimeType = MimeTypes.getDefaultMimeTypes().forName(type);
    log.info("mimeType: {}", mimeType);
  }

}
