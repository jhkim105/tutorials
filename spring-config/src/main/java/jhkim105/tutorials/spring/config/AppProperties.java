package jhkim105.tutorials.spring.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private String name;
  private String storagePath;
  private String randomValue;

  private InnerProperties inner;



  @NestedConfigurationProperty // 외부에 있는 클래스 사용시 필요. 없어도 값은 할당되지만 application.yml 에서 Cannot resolve configuration property
  private SampleProperties sample;

  @Getter
  @Setter
  @ToString
  public static class InnerProperties {
    private String name;
  }
}
