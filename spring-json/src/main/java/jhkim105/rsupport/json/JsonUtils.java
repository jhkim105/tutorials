package jhkim105.rsupport.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {
  public static String toString(Object obj) {
    return toString(obj, false);
  }

  public static String toString(Object obj, boolean prettyFormat) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    if (prettyFormat)
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(String.format("convert to json string error..,%s", e), e);
    }
  }

  public static <T> T toObject(String str, Class<T> clazz) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return (T) mapper.readValue(str, clazz);
    } catch (Exception e) {
      throw new RuntimeException(String.format("convert to object error..,%s", e), e);
    }
  }

}
