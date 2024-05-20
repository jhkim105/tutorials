package jhkim105.tutorials.spring.files;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private String storagePath;
  private final Map<String, ResourceMapping> resourceMappings = new HashMap<>();


  @Getter
  @Setter
  @ToString
  public static class ResourceMapping {
    private String path;
    private String[] locations;
  }
}
