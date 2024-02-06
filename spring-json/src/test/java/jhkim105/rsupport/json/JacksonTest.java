package jhkim105.rsupport.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;


class JacksonTest {

  @Test
  void test() {
    SampleDateGroupedRecord sampleDateGroupedRecord = new SampleDateGroupedRecord(LocalDate.now(), LocalDateTime.now(), List.of(new Sample("sample1")));
    System.out.println(toString(sampleDateGroupedRecord, true));
  }

  private String toString(Object obj, boolean prettyFormat) {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    if (prettyFormat)
      mapper.enable(SerializationFeature.INDENT_OUTPUT);
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(String.format("convert to json string error..,%s", e), e);
    }
  }

}
