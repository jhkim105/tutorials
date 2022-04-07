package jhkim105.tutorials.commons.configuration2;

import javax.annotation.Resource;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {

  @Resource(name = "runtimePropertiesBuilder")
  private ReloadingFileBasedConfigurationBuilder runtimePropertiesBuilder;

  @Resource(name = "runtimePropertiesBuilder2")
  private ReloadingFileBasedConfigurationBuilder runtimePropertiesBuilder2;

  @GetMapping("/runtime")
  public ResponseEntity<String> getRuntimeProperty(String key) throws ConfigurationException {
    return ResponseEntity.ok(runtimePropertiesBuilder.getConfiguration().getString(key));
  }

  @GetMapping("/runtime2")
  public ResponseEntity<String> getRuntimeProperty2(String key) throws ConfigurationException {
    return ResponseEntity.ok(runtimePropertiesBuilder2.getConfiguration().getString(key));
  }
}
