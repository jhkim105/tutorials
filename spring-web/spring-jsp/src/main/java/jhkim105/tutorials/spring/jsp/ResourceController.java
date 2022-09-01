package jhkim105.tutorials.spring.jsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utils.FileUtils;


@RestController
@RequestMapping("/resources")
public class ResourceController {
  
  @GetMapping("/prop")
  public String coreProperties() throws FileNotFoundException {
    File file = ResourceUtils.getFile("classpath:application.properties");
    return FileUtils.readFileToString(file);
  }

  @GetMapping("/prop2")
  public String coreProperties2() throws IOException {
    File file = new ClassPathResource("application.properties").getFile();
    return FileUtils.readFileToString(file);
  }
}
