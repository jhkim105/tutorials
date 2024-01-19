package jhkim105.tutorials.io;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


@Slf4j
class FileTests {


  @Test
  void path() {
    assertThat(Paths.get("src/test/resources", "img.png").toString()).isEqualTo("src/test/resources/img.png");
    assertThat(Paths.get("src/test/resources", "img.png").toFile().getPath()).isEqualTo("src/test/resources/img.png");
  }
}
