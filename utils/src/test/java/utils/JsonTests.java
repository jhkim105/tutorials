package utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JsonTests {

  @Test
  void includeNotNull() {
    Sample sample = new Sample();
    String json = JsonUtils.toString(sample);
    log.debug(json);
    assertThat(json).isEqualTo("{\"id\":\"\"}");
  }

  @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE)
  @JsonInclude(value = Include.NON_NULL)
  public static class Sample {
    private String id;
    private String name;

    @JsonProperty
    public String getId() {
      return StringUtils.defaultString(this.id);
    }
  }
}
