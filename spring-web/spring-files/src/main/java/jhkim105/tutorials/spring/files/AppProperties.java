package jhkim105.tutorials.spring.files;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ToString
@ConstructorBinding
@ConfigurationProperties(prefix = "app")
@RequiredArgsConstructor
public class AppProperties {

  private final String storagePath;

}
