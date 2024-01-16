package jhkim105.tutorials.spring.mvc.file;

import java.io.File;
import java.io.FileNotFoundException;
import jhkim105.tutorials.spring.mvc.common.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

@Slf4j
class ResourceTests {


  @Test
  void testResourceUtils() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:data/test.dat");
    log.debug(FileUtils.readFileToString(file));
  }

}
