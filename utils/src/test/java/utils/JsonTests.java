package utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class JsonTests {

  @Test
  void includeNotNull() {
    Sample sample = new Sample();
    String jsonString = JsonUtils.toString(sample);
    log.debug(jsonString);
    assertThat(jsonString).isEqualTo("{\"id\":\"\"}");
  }

  @Test
  void jsonPath() {
    Sample sample = new Sample();
    sample.name = "Name";
    String jsonString = JsonUtils.toString(sample);
    String value = JsonPath.parse(jsonString).read("$.name");
    assertThat(value).isEqualTo(sample.name);
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
