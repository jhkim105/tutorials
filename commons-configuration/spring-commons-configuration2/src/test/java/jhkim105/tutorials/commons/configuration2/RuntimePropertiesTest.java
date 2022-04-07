package jhkim105.tutorials.commons.configuration2;

import javax.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RuntimePropertiesTest {

  @Resource(name = "runtimePropertiesBuilder")
  private ReloadingFileBasedConfigurationBuilder runtimePropertiesBuilder;

  @SneakyThrows
  @Test
  void test() {
    Assertions.assertThat(runtimePropertiesBuilder.getConfiguration().getString("prop1")).isEqualTo("a");
    Assertions.assertThat(runtimePropertiesBuilder.getConfiguration().getString("prop2")).isEqualTo("b");
  }
}
