package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonUtils {

  public static String toString(Object obj) {
    return toString(obj, false);
  }

  public static String toString(Object obj, boolean prettyFormat) {
    ObjectMapper mapper = new ObjectMapper();
    if (prettyFormat)
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    String result;
    try {
      result = mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(String.format("convert to json string error..error:%s, object:%s",
          e.toString(), obj.toString()), e);
    }
    return result;
  }

  public static <T> T toObject(String str, Class<?> clazz) {

    ObjectMapper mapper = new ObjectMapper();
    T result;

    try {
      result = (T)mapper.readValue(str, clazz);
    } catch (Exception e) {
      throw new RuntimeException(String.format("convert to object error..error:%s, targetClass:%s, jsonString:%s"
          , e.toString(), clazz.getCanonicalName(), str), e);
    }
    return result;
  }
}
